package org.freemars.ui.mainmenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.InputStream;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuWindow extends JDialog {

    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;
    private JButton continueButton;
    private JButton newButton;
    private JButton openButton;
    private JButton quickLoadButton;
    private JButton preferencesButton;
    private JButton editorButton;
    private JButton marsopediaButton;
    private JButton exitButton;
    static final int frameWidth = 250;
    static final int frameHeight = 470;

    public MenuWindow(JFrame frame) {
        super(frame);
        setTitle("Free Mars");
        setModal(true);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - frameWidth) / 2, (screenSize.height - frameHeight) / 2, frameWidth, frameHeight);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        try {
            InputStream is = this.getClass().getResourceAsStream("/fonts/GUNSHIP2.TTF");
            Font mainMenuWindowFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 12);
            setFont(mainMenuWindowFont);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeWidgets();
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        getContinueButton().setFont(font);
        getNewButton().setFont(font);
        getOpenButton().setFont(font);
        getQuickLoadButton().setFont(font);
        getPreferencesButton().setFont(font);
        getEditorButton().setFont(font);
        getMarsopediaButton().setFont(font);
        getExitButton().setFont(font);
    }

    public void setContinueButtonAction(Action action) {
        getContinueButton().setAction(action);
    }

    public void setNewButtonAction(Action action) {
        getNewButton().setAction(action);
    }

    public void setOpenButtonAction(Action action) {
        getOpenButton().setAction(action);
    }

    public void setQuickLoadButtonAction(Action action) {
        getQuickLoadButton().setAction(action);
    }

    public void setPreferencesButtonAction(Action action) {
        getPreferencesButton().setAction(action);
    }

    public void setEditorButtonAction(Action action) {
        getEditorButton().setAction(action);
    }

    public void setMarsopediaButtonAction(Action action) {
        getMarsopediaButton().setAction(action);
    }

    public void setExitButtonAction(Action action) {
        getExitButton().setAction(action);
    }

    private void initializeWidgets() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getTopPanel(), BorderLayout.PAGE_START);
        getContentPane().add(getLeftPanel(), BorderLayout.LINE_START);
        getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
        getContentPane().add(getRightPanel(), BorderLayout.LINE_END);
        getContentPane().add(getBottomPanel(), BorderLayout.PAGE_END);
    }

    private JPanel getTopPanel() {
        if (topPanel == null) {
            topPanel = new JPanel();
            topPanel.setBackground(Color.LIGHT_GRAY);
        }
        return topPanel;
    }

    private JPanel getLeftPanel() {
        if (leftPanel == null) {
            leftPanel = new JPanel();
            leftPanel.setBackground(Color.LIGHT_GRAY);
        }
        return leftPanel;
    }

    private JPanel getCenterPanel() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new GridLayout(0, 1, 0, 20));
            centerPanel.setBackground(Color.LIGHT_GRAY);
            centerPanel.add(getContinueButton());
            centerPanel.add(getNewButton());
            centerPanel.add(getOpenButton());
            centerPanel.add(getQuickLoadButton());
            centerPanel.add(getPreferencesButton());
            centerPanel.add(getEditorButton());
            centerPanel.add(getMarsopediaButton());
            centerPanel.add(getExitButton());
        }
        return centerPanel;
    }

    private JPanel getRightPanel() {
        if (rightPanel == null) {
            rightPanel = new JPanel();
            rightPanel.setBackground(Color.LIGHT_GRAY);
        }
        return rightPanel;
    }

    private JPanel getBottomPanel() {
        if (bottomPanel == null) {
            bottomPanel = new JPanel();
            bottomPanel.setBackground(Color.LIGHT_GRAY);
        }
        return bottomPanel;
    }

    private JButton getContinueButton() {
        if (continueButton == null) {
            continueButton = new JButton();
        }
        return continueButton;
    }

    private JButton getNewButton() {
        if (newButton == null) {
            newButton = new JButton();
        }
        return newButton;
    }

    private JButton getOpenButton() {
        if (openButton == null) {
            openButton = new JButton();
        }
        return openButton;
    }

    private JButton getQuickLoadButton() {
        if (quickLoadButton == null) {
            quickLoadButton = new JButton();
        }
        return quickLoadButton;
    }

    private JButton getPreferencesButton() {
        if (preferencesButton == null) {
            preferencesButton = new JButton();
        }
        return preferencesButton;
    }

    private JButton getEditorButton() {
        if (editorButton == null) {
            editorButton = new JButton();
        }
        return editorButton;
    }

    private JButton getMarsopediaButton() {
        if (marsopediaButton == null) {
            marsopediaButton = new JButton();
        }
        return marsopediaButton;
    }

    private JButton getExitButton() {
        if (exitButton == null) {
            exitButton = new JButton();
        }
        return exitButton;
    }
}
