package org.freemars.controller.action.file;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.freemars.ai.AIPlayer;
import org.freemars.command.AddFreeMarsAIPlayerCommand;
import org.freemars.command.AddFreeMarsPlayerCommand;
import org.freemars.command.ResetFreeMarsModelCommand;
import org.freemars.controller.FreeMarsController;
import org.freemars.controller.viewcommand.ClearPaintModelsCommand;
import org.freemars.controller.viewcommand.RepaintMapPanelCommand;
import org.freemars.controller.viewcommand.UpdateExploredAreaPaintModelsCommand;
import org.freemars.earth.Earth;
import org.freemars.earth.EarthXMLConverter;
import org.freemars.message.FirstArrivalToMarsMessage;
import org.freemars.mission.MissionReader;
import org.freemars.model.wizard.newgame.NewGameOptions;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.wizard.newgame.NewGameWizard;
import org.freerealm.Realm;
import org.freerealm.command.AddRequiredPopulationResourceCommand;
import org.freerealm.command.CreateMapCommand;
import org.freerealm.command.InitializeRealmCommand;
import org.freerealm.command.ReadMapFileCommand;
import org.freerealm.command.SetActivePlayerCommand;
import org.freerealm.diplomacy.Diplomacy;
import org.freerealm.nation.Nation;
import org.freerealm.player.Player;
import org.freerealm.resource.Resource;
import org.freerealm.tile.TileType;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Deniz ARIKAN
 */
public class NewGameAction extends AbstractAction {

    private final Random rand = new Random(System.currentTimeMillis());
    private final FreeMarsController freeMarsController;
    private Realm realm;

    public NewGameAction(FreeMarsController controller) {
        super("New game");
        this.freeMarsController = controller;
    }

    public void actionPerformed(ActionEvent e) {
        if (!freeMarsController.isMainMenuFrameVisible()) {
            Object[] options = {"Yes, start new game", "No, thanks"};
            int value = JOptionPane.showOptionDialog(freeMarsController.getCurrentFrame(),
                    "Do you want to start a new game game?",
                    "New game",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (value != JOptionPane.YES_OPTION) {
                return;
            }
        }
        realm = freeMarsController.getFreeMarsModel().getRealm();
        freeMarsController.execute(new ResetFreeMarsModelCommand(freeMarsController.getFreeMarsModel()));
        freeMarsController.execute(new InitializeRealmCommand(freeMarsController.getFreeMarsModel().getRealm()));
        freeMarsController.execute(new AddRequiredPopulationResourceCommand(realm, 0, 1));
        freeMarsController.execute(new AddRequiredPopulationResourceCommand(realm, 1, 6));
        freeMarsController.execute(new AddRequiredPopulationResourceCommand(realm, 2, 1));

        DOMParser builder = new DOMParser();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(ClassLoader.getSystemResourceAsStream("config/earth.xml"));
        InputSource inputSource = new InputSource(bufferedInputStream);
        try {
            builder.parse(inputSource);
        } catch (SAXException exception) {
        } catch (IOException exception) {
        }
        Node earthNode = builder.getDocument().getFirstChild();
        Earth earth = new EarthXMLConverter().initializeFromNode(realm, earthNode);
        freeMarsController.getFreeMarsModel().setEarth(earth);

        if (!freeMarsController.isMainMenuFrameVisible()) {
            freeMarsController.displayMainMenuFrame();
        }
        freeMarsController.hideMainMenuWindow();
        NewGameOptions newGameOptions = freeMarsController.displayNewGameWizard();
        if (newGameOptions.getReturnCode() == NewGameWizard.NEW_GAME_FINISH_RETURN_CODE) {
            if (newGameOptions.getMapType() == NewGameOptions.CUSTOMIZED_MAP) {
                TreeMap<TileType, Integer> tileTypes = newGameOptions.getTileTypes();
                Iterator<TileType> iterator = tileTypes.keySet().iterator();
                while (iterator.hasNext()) {
                    TileType tileType = iterator.next();
                    int probability = tileTypes.get(tileType);
                    tileType.setProbability(probability);
                }
                freeMarsController.execute(new CreateMapCommand(realm, newGameOptions.getMapWidth(), newGameOptions.getMapHeight()));
            } else {
                String premadeMapPath = newGameOptions.getPremadeMapPath();
                InputStream inputStream;
                if (premadeMapPath.startsWith("maps/")) {
                    inputStream = ClassLoader.getSystemResourceAsStream(premadeMapPath);
                } else {
                    try {
                        inputStream = new FileInputStream(new File(premadeMapPath));
                    } catch (Exception exception) {
                        inputStream = null;
                    }
                }
                freeMarsController.execute(new ReadMapFileCommand(realm, inputStream));
            }
            freeMarsController.getFreeMarsModel().setObjectives(newGameOptions.getObjectives());

            Player firstPlayer = null;
            Iterator<Nation> nationsIterator = newGameOptions.getNationsIterator();
            while (nationsIterator.hasNext()) {
                Nation nation = nationsIterator.next();
                Color primaryColor = newGameOptions.getNationPrimaryColor(nation);
                Color secondaryColor = newGameOptions.getNationSecondaryColor(nation);
                Player player;
                if (nation.equals(newGameOptions.getHumanPlayerNation())) {
                    player = new FreeMarsPlayer(freeMarsController.getFreeMarsModel().getRealm());
                    FirstArrivalToMarsMessage message = new FirstArrivalToMarsMessage();
                    message.setSubject("Welcome to Red planet!");
                    message.setTurnSent(0);
                    message.setText("After 6 months of voyage you have arrived to Mars.\nYour shuttle is waiting in orbit.");
                    player.addMessage(message);

                    freeMarsController.execute(new AddFreeMarsPlayerCommand(freeMarsController, player, nation));
                } else {
                    player = new AIPlayer(freeMarsController.getFreeMarsModel().getRealm());
                    freeMarsController.execute(new AddFreeMarsAIPlayerCommand(freeMarsController, (AIPlayer) player, nation));
                }
                player.setPrimaryColor(primaryColor);
                player.setSecondaryColor(secondaryColor);
                if (nation.equals(newGameOptions.getHumanPlayerNation())) {
                    freeMarsController.getFreeMarsModel().setHumanPlayer(player);
                }
                if (firstPlayer == null) {
                    firstPlayer = player;
                }
            }
            new MissionReader().readMissions(freeMarsController);

            earth.setDefaultResourceQuantities(realm);

            Logger.getLogger(NewGameAction.class).info("Starting a new game.");
            freeMarsController.executeViewCommand(new ClearPaintModelsCommand(freeMarsController));
            freeMarsController.executeViewCommand(new UpdateExploredAreaPaintModelsCommand(freeMarsController));
            freeMarsController.executeViewCommand(new RepaintMapPanelCommand(freeMarsController));
            freeMarsController.displayGameFrame();
            freeMarsController.execute(new SetActivePlayerCommand(realm, firstPlayer));
        } else {
            freeMarsController.displayMainMenuFrame();
            freeMarsController.displayMainMenuWindow();
        }

    }

}
