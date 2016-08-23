package org.freemars.controller;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import org.commandexecutor.Command;
import org.freemars.FreeMarsExecutionThread;
import org.freemars.FreeMarsUpdateManagerThread;
import org.freemars.controller.action.DisplayHelpContentsAction;
import org.freemars.controller.action.DisplayMapEditorAction;
import org.freemars.controller.action.EndTurnAction;
import org.freemars.controller.action.MiniMapDefaultZoomAction;
import org.freemars.controller.action.file.ContinueGameAction;
import org.freemars.controller.action.file.DisplayMainMenuAction;
import org.freemars.controller.action.file.DisplayPreferencesDialogAction;
import org.freemars.controller.action.file.ExitGameAction;
import org.freemars.controller.action.file.LoadGameAction;
import org.freemars.controller.action.file.NewGameAction;
import org.freemars.controller.action.file.QuickLoadGameAction;
import org.freemars.controller.listener.MapPanelMouseListener;
import org.freemars.controller.listener.MiniMapPanelMouseListener;
import org.freemars.controller.listener.UnitDetailsPanelMouseListener;
import org.freemars.controller.shortcut.KeyboardShortcutHelper;
import org.freemars.controller.viewcommand.ViewCommand;
import org.freemars.controller.viewcommand.ViewCommandExecutionThread;
import org.freemars.editor.controller.EditorController;
import org.freemars.model.FreeMarsModel;
import org.freemars.model.wizard.newgame.NewGameOptions;
import org.freemars.player.FreeMarsPlayer;
import org.freemars.ui.GameFrame;
import org.freemars.ui.help.FreeMarsHelpDialog;
import org.freemars.ui.mainmenu.MainMenuFrame;
import org.freemars.ui.player.preferences.FreeMarsPreferences;
import org.freemars.ui.wizard.newgame.NewGameWizard;
import org.freerealm.command.InitializeRealmCommand;
import org.freerealm.unit.Unit;

/**
 * @author Deniz ARIKAN
 */
public class FreeMarsController {

	private FreeMarsExecutionThread freeMarsExecutionThread;
	private FreeMarsUpdateManagerThread freeMarsUpdateManagerThread;

	private final FreeMarsModel freeMarsModel;
	private final ActionManager actionManager;
	private JFrame currentFrame;
	private MainMenuFrame mainMenuFrame;
	private GameFrame gameFrame;
	private FreeMarsHelpDialog helpDialog;
	private final AutosaveManager autosaveManager;
	private AutoEndTurnHandler autoEndTurnHandler;
	private final MissionHelper missionHelper;
	private ViewCommandExecutionThread viewCommandExecutionThread;

	public FreeMarsController() {
		freeMarsModel = new FreeMarsModel();
		actionManager = new ActionManager(this);
		autosaveManager = new AutosaveManager(freeMarsModel);
		missionHelper = new MissionHelper();
		readPreferences();
		/*
		preCommandHandlers = new HashMap<Class, PreCommandHandler>();
		preCommandHandlers.put(AttackUnitCommand.class, new AttackUnitPreCommandHandler());
		postCommandHandlers = new HashMap<Integer, PostCommandHandler>();
		postCommandHandlers.put(CommandResult.ACTIVE_UNIT_UPDATE, new ActiveUnitHandler());
		postCommandHandlers.put(CommandResult.ACTIVE_PLAYER_UPDATE, new ActivePlayerHandler());
		postCommandHandlers.put(CommandResult.NEW_TURN_UPDATE, new NewTurnHandler());
		postCommandHandlers.put(CommandResult.MISSION_ASSIGNED_UPDATE, new MissionAssignedHandler());
		postCommandHandlers.put(CommandResult.UNIT_MOVEMENT_UPDATE, new UnitMovementHandler());
		postCommandHandlers.put(CommandResult.UNIT_ORDER_ASSIGNED_UPDATE, new UnitOrderAssignedHandler());
		postCommandHandlers.put(CommandResult.UNIT_ORDER_EXECUTED_UPDATE, new UnitOrderExecutedHandler());
		postCommandHandlers.put(CommandResult.VEGETATION_CHANGED_UPDATE, new VegetationChangedHandler());
		postCommandHandlers.put(CommandResult.NEW_SETTLEMENT_UPDATE, new NewSettlementHandler());
		postCommandHandlers.put(CommandResult.UNIT_SKIPPED_UPDATE, new UnitSkippedHandler());
		postCommandHandlers.put(CommandResult.UNIT_ORDERS_CLEARED_UPDATE, new UnitOrdersClearedHandler());
		postCommandHandlers.put(CommandResult.UNIT_STATUS_ACTIVATED_UPDATE, new UnitStatusActivatedHandler());
		postCommandHandlers.put(CommandResult.UNIT_STATUS_SUSPENDED_UPDATE, new UnitStatusSuspendedHandler());
		postCommandHandlers.put(CommandResult.EXPLORED_COORDINATES_ADDED_TO_PLAYER_UPDATE, new ExploredCoordinatesAddedToPlayerHandler());
		postCommandHandlers.put(CommandResult.COLLECTABLE_PROCESSED_UPDATE, new CollectableProcessedHandler());
		postCommandHandlers.put(CommandResult.REALM_INITIALIZE_UPDATE, new RealmInitializeHandler());
		postCommandHandlers.put(CommandResult.SETTLEMENT_CAPTURED_UPDATE, new SettlementCapturedHandler());
		postCommandHandlers.put(CommandResult.UNIT_REMOVED_UPDATE, new UnitRemovedHandler());
		postCommandHandlers.put(CommandResult.SETTLEMENT_POPULATION_UPDATED, new SettlementPopulationUpdatedHandler());
		postCommandHandlers.put(CommandResult.DIPLOMATIC_STATUS_UPDATE, new DiplomaticStatusUpdatedHandler());
		postCommandHandlers.put(CommandResult.WEALTH_GIFT_SENT_UPDATE, new WealthGiftSentHandler());
		postCommandHandlers.put(CommandResult.RESOURCE_GIFT_SENT_UPDATE, new ResourceGiftSentHandler());
		postCommandHandlers.put(CommandResult.UNIT_ATTACKED_UPDATE, new UnitAttackedHandler());
		postCommandHandlers.put(CommandResult.PLAYER_REMOVED_UPDATE, new PlayerRemovedHandler());
		postCommandHandlers.put(FreeMarsModel.PLAYER_DECLARED_INDEPENDENCE_UPDATE, new PlayerDeclaredIndependenceHandler());
		postCommandHandlers.put(FreeMarsModel.SPACESHIPS_SEIZED_UPDATE, new SpaceshipsSeizedHandler());
		postCommandHandlers.put(FreeMarsModel.UNITS_SEIZED_UPDATE, new UnitsSeizedHandler());
		postCommandHandlers.put(FreeMarsModel.EXPEDITIONARY_FORCE_LANDED_UPDATE, new ExpeditionaryForceLandedHandler());
		postCommandHandlers.put(FreeMarsModel.EXPEDITIONARY_FORCE_CHANGED_UPDATE, new ExpeditionaryForceChangedHandler());
		postCommandHandlers.put(FreeMarsModel.EARTH_TAX_RATE_CHANGED_UPDATE, new EarthTaxRateChangedHandler());
		postCommandHandlers.put(FreeMarsModel.RANDOM_EVENT_UPDATE, new RandomEventHandler());

		postCommandHandlers.put(CommandResult.PLAYER_END_TURN_UPDATE, new PlayerEndTurnHandler());
		postCommandHandlers.put(CommandResult.TURN_ENDED_UPDATE, new TurnEndedHandler());
		*/
	}

	public void addCommandToQueue(org.commandexecutor.Command command) {
		getFreeMarsExecutionThread().addCommandToQueue(command);
	}

	public void execute(Command command) {
		getFreeMarsExecutionThread().execute(command);
	}

	public void startFreeMarsThreads() {
		getFreeMarsExecutionThread().start();
		getFreeMarsUpdateManagerThread().start();
	}

	private FreeMarsExecutionThread getFreeMarsExecutionThread() {
		if (freeMarsExecutionThread == null) {
			freeMarsExecutionThread = new FreeMarsExecutionThread(getFreeMarsUpdateManagerThread());
		}
		return freeMarsExecutionThread;
	}

	public FreeMarsUpdateManagerThread getFreeMarsUpdateManagerThread() {
		if (freeMarsUpdateManagerThread == null) {
			freeMarsUpdateManagerThread = new FreeMarsUpdateManagerThread(this);
		}
		return freeMarsUpdateManagerThread;
	}

	public void startViewCommandExecutionThread() {
		getViewCommandExecutionThread().start();
	}

	public void executeViewCommand(ViewCommand viewCommand) {
		getViewCommandExecutionThread().addCommandToQueue(viewCommand);
	}

	public void executeViewCommandImmediately(ViewCommand viewCommand) {
		getViewCommandExecutionThread().executeCommand(viewCommand);
	}

	public ViewCommandExecutionThread getViewCommandExecutionThread() {
		if (viewCommandExecutionThread == null) {
			viewCommandExecutionThread = new ViewCommandExecutionThread(this);
		}
		return viewCommandExecutionThread;
	}

	public void updateGameFrame() {
		getGameFrame().update();
	}

	public void updateActions() {
		actionManager.refresh();
	}
/*
	private void update(CommandResult commandResult) {
		if (freeMarsModel.getMode() != FreeMarsModel.SIMULATION_MODE) {
			actionManager.refresh();
		}
		int updateType = commandResult.getUpdateType();
		getAutoEndTurnHandler().handleUpdate(this, commandResult);
		if (freeMarsModel.getMode() != FreeMarsModel.SIMULATION_MODE) {
			if (updateType != CommandResult.NO_UPDATE) {
				if (getFreeMarsModel().isHumanPlayerActive()) {
					updateGameFrame();
				}
			}
		}
	}
*/
	public AutoEndTurnHandler getAutoEndTurnHandler() {
		if (autoEndTurnHandler == null) {
			autoEndTurnHandler = new AutoEndTurnHandler();
		}
		return autoEndTurnHandler;
	}

	public AbstractAction getAction(int actionId) {
		return actionManager.getAction(actionId);
	}

	public boolean isActivateEnabledForUnit(Unit unit) {
		return actionManager.isActivateEnabledForUnit(getFreeMarsModel(), unit);
	}

	public void displayMainMenuFrame() {
		displayFrame(getMainMenuFrame());
	}

	public void displayGameFrame() {
		actionManager.refresh();
		displayFrame(getGameFrame());
	}

	public void displayEditorFrame() {
		execute(new InitializeRealmCommand(getFreeMarsModel().getRealm()));
		getFreeMarsModel().getRealm().setMap(null);
		EditorController editorController = new EditorController(getFreeMarsModel().getRealm());
		editorController.setQuitEditorAction(new DisplayMainMenuAction(this));
		editorController.displayEditorFrame(getCurrentFrame().getWidth(), getCurrentFrame().getHeight());
		hideCurrentFrame();
	}

	public void displayMainMenuWindow() {
		getMainMenuFrame().displayMainMenuWindow();
	}

	public void hideMainMenuWindow() {
		getMainMenuFrame().hideMainMenuWindow();
	}

	public NewGameOptions displayNewGameWizard() {
		NewGameWizard newGameWizard = new NewGameWizard(getMainMenuFrame(), this);
		return newGameWizard.showNewGameWizardDialog();
	}

	public boolean isMainMenuFrameVisible() {
		return getMainMenuFrame().isVisible();
	}

	public JFrame getCurrentFrame() {
		return currentFrame;
	}

	public FreeMarsModel getFreeMarsModel() {
		return freeMarsModel;
	}

	public FreeMarsHelpDialog getHelpDialog() {
		if (helpDialog == null) {
			helpDialog = new FreeMarsHelpDialog(getCurrentFrame());
		}
		return helpDialog;
	}

	public void assignMissions(FreeMarsPlayer freeMarsPlayer) {
		missionHelper.assignMissions(this, freeMarsPlayer);
	}

	public void clearMissions() {
		missionHelper.clearMissions();
	}

	public void addMissionAssignment(MissionAssignment missionAssignment) {
		missionHelper.addMissionAssignment(missionAssignment);
	}

	protected AutosaveManager getAutosaveManager() {
		return autosaveManager;
	}

	private void displayFrame(JFrame frame) {
		if (getCurrentFrame() != null) {
			if (getCurrentFrame().equals(frame)) {
				return;
			}
		}
		boolean fullScreen = Boolean.valueOf(getFreeMarsModel().getFreeMarsPreferences().getProperty("full_screen"));
		if (getCurrentFrame() != null) {
			getCurrentFrame().setVisible(false);
			if (!getCurrentFrame().equals(frame)) {
				getCurrentFrame().dispose();
			}
		}
		if (fullScreen) {
			if (!frame.isUndecorated()) {
				frame.setVisible(false);
				frame.dispose();
				frame.setUndecorated(true);
			}
			if (frame.isResizable()) {
				frame.setResizable(false);
			}
		} else {
			if (frame.isUndecorated()) {
				frame.setVisible(false);
				frame.dispose();
				frame.setUndecorated(false);
			}
			if (!frame.isResizable()) {
				frame.setResizable(true);
			}
			frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		}
		frame.setVisible(true);
		setCurrentFrame(frame);
	}

	public void reDisplayCurrentFrame() {
		boolean isMainMenuDisplayed = false;
		if (getMainMenuFrame().isVisible() && getMainMenuFrame().getMenuWindow().isVisible()) {
			isMainMenuDisplayed = true;
		}
		displayFrame(getCurrentFrame());
		if (isMainMenuDisplayed) {
			getMainMenuFrame().displayMainMenuWindow();
		}
	}

	private void hideCurrentFrame() {
		if (getCurrentFrame() != null) {
			getCurrentFrame().setVisible(false);
			setCurrentFrame(null);
		}
	}

	private void setCurrentFrame(JFrame frame) {
		this.currentFrame = frame;
	}

	private MainMenuFrame getMainMenuFrame() {
		if (mainMenuFrame == null) {
			mainMenuFrame = new MainMenuFrame();
			mainMenuFrame.getMenuWindow().setContinueButtonAction(new ContinueGameAction(this));
			mainMenuFrame.getMenuWindow().setNewButtonAction(new NewGameAction(this));
			mainMenuFrame.getMenuWindow().setOpenButtonAction(new LoadGameAction(this));
			mainMenuFrame.getMenuWindow().setQuickLoadButtonAction(new QuickLoadGameAction(this));
			mainMenuFrame.getMenuWindow().setPreferencesButtonAction(new DisplayPreferencesDialogAction(this));
			mainMenuFrame.getMenuWindow().setEditorButtonAction(new DisplayMapEditorAction(this));
			mainMenuFrame.getMenuWindow().setMarsopediaButtonAction(new DisplayHelpContentsAction(this, null));
			mainMenuFrame.getMenuWindow().setExitButtonAction(new ExitGameAction(this));
		}
		return mainMenuFrame;
	}

	public GameFrame getGameFrame() {
		if (gameFrame == null) {
			gameFrame = new GameFrame(getFreeMarsModel());
		}
		return gameFrame;
	}

	public void initGameFrame() {
		freeMarsModel.setGameFrame(getGameFrame());
		KeyboardShortcutHelper.assignKeyboardShortcuts(getGameFrame().getMapPanel(), this);
		MenuActionAssignmentHelper.assignMenuActions(this, getGameFrame());
		MapPanelMouseListener mapPanelMouseListener = new MapPanelMouseListener(this, getGameFrame().getMapPanel());
		getGameFrame().getMapPanel().addMouseListener(mapPanelMouseListener);
		getGameFrame().getMapPanel().addMouseMotionListener(mapPanelMouseListener);
		getGameFrame().getMapPanel().addMouseWheelListener(mapPanelMouseListener);
		getGameFrame().getMiniMapPanel().addMouseListener(new MiniMapPanelMouseListener(this, getGameFrame().getMiniMapPanel()));
		getGameFrame().setMiniMapZoomInButtonAction(getAction(ActionManager.MINI_MAP_ZOOM_IN_ACTION));
		getGameFrame().setMiniMapZoomOutButtonAction(getAction(ActionManager.MINI_MAP_ZOOM_OUT_ACTION));
		getGameFrame().setMiniMapDefaultZoomButtonAction(new MiniMapDefaultZoomAction(this));
		getGameFrame().setEndTurnButtonAction(new EndTurnAction(this));
		getGameFrame().setUnitDetailsPanelMouseListener(new UnitDetailsPanelMouseListener(this));
	}

	private void readPreferences() {
		FreeMarsPreferences freeMarsPreferences = new FreeMarsPreferences();
		freeMarsPreferences.readPreferences();
		getFreeMarsModel().setFreeMarsPreferences(freeMarsPreferences);
	}

}
