package eveposmad;
import java.awt.event.KeyEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import com.beimin.eveapi.account.characters.*;
import com.beimin.eveapi.corporation.starbase.list.*;
import com.beimin.eveapi.corporation.starbase.detail.*;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
/**
 * @author James Bacon
 */
public class EvePosMad_View extends FrameView {

    public EvePosMad_View(SingleFrameApplication app) {
        super(app);
        savedMonitorSessions = new DefaultListModel();
        savedDesignSessions = new DefaultListModel();

        selectedModuleTableModel = new DefaultTableModel(new String[]{"Name","CPU","Power"}, 0){
            @Override public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        initComponents();
        database = new EvePosMad_Database();
        
        moduleListJTable.setTransferHandler(new TransferHandler(){
            @Override public boolean canImport(TransferHandler.TransferSupport info)
            { return false; }

            @Override public int getSourceActions(JComponent c) { return COPY; }

            @Override protected Transferable createTransferable(JComponent c) {
                JTable table = (JTable) c;
                String value = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
                return new StringSelection(value);
            }
        });

        String[] sessionNameArray = database.getSavedSessionNames();
        if(sessionNameArray!=null)
        {
            for(String sessionName : sessionNameArray)
            {
                savedMonitorSessions.addElement(sessionName);
            }
        }

        sessionNameArray = database.getDesignSessionNames();
        if(sessionNameArray!=null)
        {
            for(String sessionName : sessionNameArray)
            {
                savedDesignSessions.addElement(sessionName);
            }
        }

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = EvePosMad_App.getApplication().getMainFrame();
            aboutBox = new EvePosMad_AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        EvePosMad_App.getApplication().show(aboutBox);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainPanel = new javax.swing.JPanel();
        westJPanel = new javax.swing.JPanel();
        savedSessionsJSplitPane = new javax.swing.JSplitPane();
        savedDesignsJPanel = new javax.swing.JPanel();
        savedDesignsJLabel = new javax.swing.JLabel();
        savedDesignsJScrollPane = new javax.swing.JScrollPane();
        savedDesignsJList = new javax.swing.JList();
        savedMonitorsJPanel = new javax.swing.JPanel();
        savedMonitorsJLabel = new javax.swing.JLabel();
        savedMonitorsJScrollPane = new javax.swing.JScrollPane();
        savedMonitorsJList = new javax.swing.JList();
        buttonJPanel = new javax.swing.JPanel();
        saveAllViewsJButton = new javax.swing.JButton();
        loadSelectedViewJButton = new javax.swing.JButton();
        deleteSelectedJButton = new javax.swing.JButton();
        renameSelectedJButton = new javax.swing.JButton();
        eastJPanel = new javax.swing.JPanel();
        searchJPanel = new javax.swing.JPanel();
        moduleSearchBoxJTextField = new javax.swing.JTextField();
        searchJButton = new javax.swing.JButton();
        closeModuleSearchViewJButton = new javax.swing.JButton();
        moduleSelectionJSplitPane = new javax.swing.JSplitPane();
        moduleSearchJScrollPane = new javax.swing.JScrollPane();
        moduleSearchJTree = new javax.swing.JTree();
        moduleListJScrollPane = new javax.swing.JScrollPane();
        moduleListJTable = new javax.swing.JTable();
        centreJPanel = new javax.swing.JPanel();
        dynamicJDesktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        newMonitorJMenuItem = new javax.swing.JMenuItem();
        newDesignJMenuItem = new javax.swing.JMenuItem();
        firstJSeparator = new javax.swing.JPopupMenu.Separator();
        preferencesJMenuItem = new javax.swing.JMenuItem();
        importPrefsJMenuItem = new javax.swing.JMenuItem();
        exportPrefsJMenuItem = new javax.swing.JMenuItem();
        secondJSeparator = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        editApiDetailsJMenuItem = new javax.swing.JMenuItem();
        editDesignSettingsJMenuItem = new javax.swing.JMenuItem();
        editMonitorSettingsJMenuItem = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        viewSavedSessionsViewJMenuItem = new javax.swing.JMenuItem();
        viewFittingPanelJMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        faqJMenuItem = new javax.swing.JMenuItem();
        wikiPagesJMenuItem = new javax.swing.JMenuItem();
        thirdJSeparator = new javax.swing.JPopupMenu.Separator();
        checkForUpdatesJMenuItem = new javax.swing.JMenuItem();
        fourthJSeparator = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setLayout(new java.awt.BorderLayout());

        westJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        westJPanel.setMaximumSize(new java.awt.Dimension(150, 344));
        westJPanel.setName("westJPanel"); // NOI18N
        westJPanel.setPreferredSize(new java.awt.Dimension(150, 344));
        westJPanel.setLayout(new java.awt.BorderLayout());

        savedSessionsJSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        savedSessionsJSplitPane.setName("savedSessionsJSplitPane"); // NOI18N

        savedDesignsJPanel.setMinimumSize(new java.awt.Dimension(140, 155));
        savedDesignsJPanel.setName("savedDesignsJPanel"); // NOI18N
        savedDesignsJPanel.setPreferredSize(new java.awt.Dimension(140, 155));
        savedDesignsJPanel.setLayout(new java.awt.BorderLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(EvePosMad_View.class);
        savedDesignsJLabel.setFont(resourceMap.getFont("savedDesignsJLabel.font")); // NOI18N
        savedDesignsJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        savedDesignsJLabel.setText(resourceMap.getString("savedDesignsJLabel.text")); // NOI18N
        savedDesignsJLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 5, 0));
        savedDesignsJLabel.setName("savedDesignsJLabel"); // NOI18N
        savedDesignsJPanel.add(savedDesignsJLabel, java.awt.BorderLayout.NORTH);

        savedDesignsJScrollPane.setName("savedDesignsJScrollPane"); // NOI18N

        savedDesignsJList.setModel(savedDesignSessions);
        savedDesignsJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        savedDesignsJList.setName("savedDesignsJList"); // NOI18N
        savedDesignsJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                savedDesignsJListValueChanged(evt);
            }
        });
        savedDesignsJScrollPane.setViewportView(savedDesignsJList);

        savedDesignsJPanel.add(savedDesignsJScrollPane, java.awt.BorderLayout.CENTER);

        savedSessionsJSplitPane.setLeftComponent(savedDesignsJPanel);

        savedMonitorsJPanel.setMinimumSize(new java.awt.Dimension(140, 156));
        savedMonitorsJPanel.setName("savedMonitorsJPanel"); // NOI18N
        savedMonitorsJPanel.setPreferredSize(new java.awt.Dimension(140, 156));
        savedMonitorsJPanel.setLayout(new java.awt.BorderLayout());

        savedMonitorsJLabel.setFont(resourceMap.getFont("savedMonitorsJLabel.font")); // NOI18N
        savedMonitorsJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        savedMonitorsJLabel.setText(resourceMap.getString("savedMonitorsJLabel.text")); // NOI18N
        savedMonitorsJLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 5, 0));
        savedMonitorsJLabel.setName("savedMonitorsJLabel"); // NOI18N
        savedMonitorsJPanel.add(savedMonitorsJLabel, java.awt.BorderLayout.NORTH);

        savedMonitorsJScrollPane.setName("savedMonitorsJScrollPane"); // NOI18N

        savedMonitorsJList.setModel(savedMonitorSessions);
        savedMonitorsJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        savedMonitorsJList.setName("savedMonitorsJList"); // NOI18N
        savedMonitorsJList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                savedMonitorsJListValueChanged(evt);
            }
        });
        savedMonitorsJScrollPane.setViewportView(savedMonitorsJList);

        savedMonitorsJPanel.add(savedMonitorsJScrollPane, java.awt.BorderLayout.CENTER);

        savedSessionsJSplitPane.setRightComponent(savedMonitorsJPanel);

        westJPanel.add(savedSessionsJSplitPane, java.awt.BorderLayout.CENTER);

        buttonJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 3, 0));
        buttonJPanel.setName("buttonJPanel"); // NOI18N
        buttonJPanel.setLayout(new java.awt.GridBagLayout());

        saveAllViewsJButton.setText(resourceMap.getString("saveAllViewsJButton.text")); // NOI18N
        saveAllViewsJButton.setEnabled(false);
        saveAllViewsJButton.setMaximumSize(new java.awt.Dimension(65, 20));
        saveAllViewsJButton.setMinimumSize(new java.awt.Dimension(65, 20));
        saveAllViewsJButton.setName("saveAllViewsJButton"); // NOI18N
        saveAllViewsJButton.setPreferredSize(new java.awt.Dimension(65, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 75;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        buttonJPanel.add(saveAllViewsJButton, gridBagConstraints);

        loadSelectedViewJButton.setText(resourceMap.getString("loadSelectedViewJButton.text")); // NOI18N
        loadSelectedViewJButton.setMaximumSize(new java.awt.Dimension(65, 20));
        loadSelectedViewJButton.setMinimumSize(new java.awt.Dimension(65, 20));
        loadSelectedViewJButton.setName("loadSelectedViewJButton"); // NOI18N
        loadSelectedViewJButton.setPreferredSize(new java.awt.Dimension(65, 20));
        loadSelectedViewJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadSelectedViewJButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 75;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        buttonJPanel.add(loadSelectedViewJButton, gridBagConstraints);

        deleteSelectedJButton.setText(resourceMap.getString("deleteSelectedJButton.text")); // NOI18N
        deleteSelectedJButton.setMaximumSize(new java.awt.Dimension(65, 20));
        deleteSelectedJButton.setMinimumSize(new java.awt.Dimension(65, 20));
        deleteSelectedJButton.setName("deleteSelectedJButton"); // NOI18N
        deleteSelectedJButton.setPreferredSize(new java.awt.Dimension(65, 20));
        deleteSelectedJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSelectedJButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 75;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        buttonJPanel.add(deleteSelectedJButton, gridBagConstraints);

        renameSelectedJButton.setText(resourceMap.getString("renameSelectedJButton.text")); // NOI18N
        renameSelectedJButton.setEnabled(false);
        renameSelectedJButton.setMaximumSize(new java.awt.Dimension(65, 20));
        renameSelectedJButton.setMinimumSize(new java.awt.Dimension(65, 20));
        renameSelectedJButton.setName("renameSelectedJButton"); // NOI18N
        renameSelectedJButton.setPreferredSize(new java.awt.Dimension(65, 20));
        renameSelectedJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameSelectedJButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 75;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        buttonJPanel.add(renameSelectedJButton, gridBagConstraints);

        westJPanel.add(buttonJPanel, java.awt.BorderLayout.NORTH);

        mainPanel.add(westJPanel, java.awt.BorderLayout.WEST);

        eastJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        eastJPanel.setMaximumSize(new java.awt.Dimension(285, 2147483647));
        eastJPanel.setMinimumSize(new java.awt.Dimension(285, 175));
        eastJPanel.setName("eastJPanel"); // NOI18N
        //jPanel5.setVisible(false);
        eastJPanel.setPreferredSize(new java.awt.Dimension(285, 80));
        eastJPanel.setLayout(new java.awt.BorderLayout());

        searchJPanel.setName("searchJPanel"); // NOI18N
        searchJPanel.setPreferredSize(new java.awt.Dimension(252, 34));
        searchJPanel.setLayout(new java.awt.GridBagLayout());

        moduleSearchBoxJTextField.setForeground(resourceMap.getColor("moduleSearchBoxJTextField.foreground")); // NOI18N
        moduleSearchBoxJTextField.setText(resourceMap.getString("moduleSearchBoxJTextField.text")); // NOI18N
        moduleSearchBoxJTextField.setMinimumSize(new java.awt.Dimension(0, 20));
        moduleSearchBoxJTextField.setName("moduleSearchBoxJTextField"); // NOI18N
        moduleSearchBoxJTextField.setPreferredSize(new java.awt.Dimension(0, 40));
        moduleSearchBoxJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                moduleSearchBoxJTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                moduleSearchBoxJTextFieldFocusLost(evt);
            }
        });
        moduleSearchBoxJTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                moduleSearchBoxJTextFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 191;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 5, 7, 5);
        searchJPanel.add(moduleSearchBoxJTextField, gridBagConstraints);

        searchJButton.setIcon(resourceMap.getIcon("searchJButton.icon")); // NOI18N
        searchJButton.setToolTipText(resourceMap.getString("searchJButton.toolTipText")); // NOI18N
        searchJButton.setMaximumSize(new java.awt.Dimension(35, 30));
        searchJButton.setMinimumSize(new java.awt.Dimension(35, 30));
        searchJButton.setName("searchJButton"); // NOI18N
        searchJButton.setPreferredSize(new java.awt.Dimension(35, 30));
        searchJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchJButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        searchJPanel.add(searchJButton, gridBagConstraints);

        closeModuleSearchViewJButton.setIcon(resourceMap.getIcon("closeModuleSearchViewJButton.icon")); // NOI18N
        closeModuleSearchViewJButton.setToolTipText(resourceMap.getString("closeModuleSearchViewJButton.toolTipText")); // NOI18N
        closeModuleSearchViewJButton.setMaximumSize(new java.awt.Dimension(35, 30));
        closeModuleSearchViewJButton.setMinimumSize(new java.awt.Dimension(35, 30));
        closeModuleSearchViewJButton.setName("closeModuleSearchViewJButton"); // NOI18N
        closeModuleSearchViewJButton.setPreferredSize(new java.awt.Dimension(45, 30));
        closeModuleSearchViewJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeModuleSearchViewJButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 2, 5);
        searchJPanel.add(closeModuleSearchViewJButton, gridBagConstraints);

        eastJPanel.add(searchJPanel, java.awt.BorderLayout.NORTH);

        moduleSelectionJSplitPane.setDividerSize(6);
        moduleSelectionJSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        moduleSelectionJSplitPane.setName("moduleSelectionJSplitPane"); // NOI18N

        moduleSearchJScrollPane.setMinimumSize(new java.awt.Dimension(150, 100));
        moduleSearchJScrollPane.setName("moduleSearchJScrollPane"); // NOI18N
        moduleSearchJScrollPane.setPreferredSize(new java.awt.Dimension(150, 200));

        moduleSearchJTree.setModel(generateDesignTree());
        moduleSearchJTree.setMinimumSize(null);
        moduleSearchJTree.setName("moduleSearchJTree"); // NOI18N
        moduleSearchJTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                moduleSearchJTreeValueChanged(evt);
            }
        });
        moduleSearchJScrollPane.setViewportView(moduleSearchJTree);

        moduleSelectionJSplitPane.setTopComponent(moduleSearchJScrollPane);

        moduleListJScrollPane.setName("moduleListJScrollPane"); // NOI18N

        moduleListJTable.setAutoCreateRowSorter(true);
        moduleListJTable.setModel(selectedModuleTableModel);
        moduleListJTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        moduleListJTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        moduleListJTable.setDragEnabled(true);
        moduleListJTable.setDropMode(javax.swing.DropMode.ON);
        moduleListJTable.setFillsViewportHeight(true);
        moduleListJTable.setName("moduleListJTable"); // NOI18N
        moduleListJTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        moduleListJTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                moduleListJTableMouseReleased(evt);
            }
        });
        moduleListJScrollPane.setViewportView(moduleListJTable);

        moduleSelectionJSplitPane.setRightComponent(moduleListJScrollPane);

        eastJPanel.add(moduleSelectionJSplitPane, java.awt.BorderLayout.CENTER);

        mainPanel.add(eastJPanel, java.awt.BorderLayout.EAST);

        centreJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centreJPanel.setMinimumSize(new java.awt.Dimension(300, 50));
        centreJPanel.setName("centreJPanel"); // NOI18N
        centreJPanel.setPreferredSize(new java.awt.Dimension(300, 50));
        centreJPanel.setLayout(new java.awt.BorderLayout());
        //jPanel4.add(new EvePosMad.EvePosMad_Design());
        //jPanel4.add(new EvePosMad.EvePosMad_Monitor());

        dynamicJDesktopPane.setBackground(resourceMap.getColor("dynamicJDesktopPane.background")); // NOI18N
        dynamicJDesktopPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        dynamicJDesktopPane.setName("dynamicJDesktopPane"); // NOI18N
        centreJPanel.add(dynamicJDesktopPane, java.awt.BorderLayout.CENTER);

        mainPanel.add(centreJPanel, java.awt.BorderLayout.CENTER);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        newMonitorJMenuItem.setText(resourceMap.getString("newMonitorJMenuItem.text")); // NOI18N
        newMonitorJMenuItem.setName("newMonitorJMenuItem"); // NOI18N
        newMonitorJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMonitorJMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newMonitorJMenuItem);

        newDesignJMenuItem.setText(resourceMap.getString("newDesignJMenuItem.text")); // NOI18N
        newDesignJMenuItem.setName("newDesignJMenuItem"); // NOI18N
        newDesignJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newDesignJMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newDesignJMenuItem);

        firstJSeparator.setName("firstJSeparator"); // NOI18N
        fileMenu.add(firstJSeparator);

        preferencesJMenuItem.setText(resourceMap.getString("preferencesJMenuItem.text")); // NOI18N
        preferencesJMenuItem.setEnabled(false);
        preferencesJMenuItem.setName("preferencesJMenuItem"); // NOI18N
        fileMenu.add(preferencesJMenuItem);

        importPrefsJMenuItem.setText(resourceMap.getString("importPrefsJMenuItem.text")); // NOI18N
        importPrefsJMenuItem.setEnabled(false);
        importPrefsJMenuItem.setName("importPrefsJMenuItem"); // NOI18N
        fileMenu.add(importPrefsJMenuItem);

        exportPrefsJMenuItem.setText(resourceMap.getString("exportPrefsJMenuItem.text")); // NOI18N
        exportPrefsJMenuItem.setEnabled(false);
        exportPrefsJMenuItem.setName("exportPrefsJMenuItem"); // NOI18N
        fileMenu.add(exportPrefsJMenuItem);

        secondJSeparator.setName("secondJSeparator"); // NOI18N
        fileMenu.add(secondJSeparator);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(EvePosMad_View.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(resourceMap.getString("editMenu.text")); // NOI18N
        editMenu.setName("editMenu"); // NOI18N

        editApiDetailsJMenuItem.setText(resourceMap.getString("editApiDetailsJMenuItem.text")); // NOI18N
        editApiDetailsJMenuItem.setEnabled(false);
        editApiDetailsJMenuItem.setName("editApiDetailsJMenuItem"); // NOI18N
        editMenu.add(editApiDetailsJMenuItem);

        editDesignSettingsJMenuItem.setText(resourceMap.getString("editDesignSettingsJMenuItem.text")); // NOI18N
        editDesignSettingsJMenuItem.setEnabled(false);
        editDesignSettingsJMenuItem.setName("editDesignSettingsJMenuItem"); // NOI18N
        editMenu.add(editDesignSettingsJMenuItem);

        editMonitorSettingsJMenuItem.setText(resourceMap.getString("editMonitorSettingsJMenuItem.text")); // NOI18N
        editMonitorSettingsJMenuItem.setEnabled(false);
        editMonitorSettingsJMenuItem.setName("editMonitorSettingsJMenuItem"); // NOI18N
        editMenu.add(editMonitorSettingsJMenuItem);

        menuBar.add(editMenu);

        viewMenu.setText(resourceMap.getString("viewMenu.text")); // NOI18N
        viewMenu.setName("viewMenu"); // NOI18N

        viewSavedSessionsViewJMenuItem.setText(resourceMap.getString("viewSavedSessionsViewJMenuItem.text")); // NOI18N
        viewSavedSessionsViewJMenuItem.setName("viewSavedSessionsViewJMenuItem"); // NOI18N
        viewSavedSessionsViewJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSavedSessionsViewJMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(viewSavedSessionsViewJMenuItem);

        viewFittingPanelJMenuItem.setText(resourceMap.getString("viewFittingPanelJMenuItem.text")); // NOI18N
        viewFittingPanelJMenuItem.setName("viewFittingPanelJMenuItem"); // NOI18N
        viewFittingPanelJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewFittingPanelJMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(viewFittingPanelJMenuItem);

        menuBar.add(viewMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        faqJMenuItem.setText(resourceMap.getString("faqJMenuItem.text")); // NOI18N
        faqJMenuItem.setEnabled(false);
        faqJMenuItem.setName("faqJMenuItem"); // NOI18N
        helpMenu.add(faqJMenuItem);

        wikiPagesJMenuItem.setText(resourceMap.getString("wikiPagesJMenuItem.text")); // NOI18N
        wikiPagesJMenuItem.setName("wikiPagesJMenuItem"); // NOI18N
        wikiPagesJMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wikiPagesJMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(wikiPagesJMenuItem);

        thirdJSeparator.setName("thirdJSeparator"); // NOI18N
        helpMenu.add(thirdJSeparator);

        checkForUpdatesJMenuItem.setText(resourceMap.getString("checkForUpdatesJMenuItem.text")); // NOI18N
        checkForUpdatesJMenuItem.setEnabled(false);
        checkForUpdatesJMenuItem.setName("checkForUpdatesJMenuItem"); // NOI18N
        helpMenu.add(checkForUpdatesJMenuItem);

        fourthJSeparator.setName("fourthJSeparator"); // NOI18N
        helpMenu.add(fourthJSeparator);

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 859, Short.MAX_VALUE)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap(633, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void savedMonitorsJListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_savedMonitorsJListValueChanged
        int tempIndex = savedMonitorsJList.getSelectedIndex();
        savedDesignsJList.clearSelection();
        savedMonitorsJList.setSelectedIndex(tempIndex);
    }//GEN-LAST:event_savedMonitorsJListValueChanged

    private void savedDesignsJListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_savedDesignsJListValueChanged
        int tempIndex = savedDesignsJList.getSelectedIndex();
        savedMonitorsJList.clearSelection();
        savedDesignsJList.setSelectedIndex(tempIndex);
    }//GEN-LAST:event_savedDesignsJListValueChanged

    private void newDesignJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newDesignJMenuItemActionPerformed
        ArrayList<String> controlTowerNames = new ArrayList<String>();
        for(String[] tower : database.getModuleList("Control Tower", "", String.valueOf(365)))
        {
            controlTowerNames.add(tower[0]);
        }
        Collections.sort(controlTowerNames);
        String selectedName = (String) JOptionPane.showInputDialog
        (
            mainPanel,
            "Please select a control tower.",
            "Control Tower Selection.",
            JOptionPane.QUESTION_MESSAGE,
            null,
            controlTowerNames.toArray(),
            null
        );
        if(selectedName==null || selectedName.isEmpty()){return;}

        String designName = JOptionPane.showInputDialog
        (
            mainPanel,
            "Please enter a suitable name for this deisgn.",
            "Enter Design Name",
            JOptionPane.PLAIN_MESSAGE
        );
        if(designName==null || designName.isEmpty()){return;}

        if(savedDesignSessions.contains(designName))
        {
            JOptionPane.showMessageDialog(mainPanel, "A design with this name already exists.");
            return;
        }

        JInternalFrame frame = new EvePosMad_Design(designName, selectedName);
        dynamicJDesktopPane.add(frame, javax.swing.JLayeredPane.DEFAULT_LAYER);
        try { frame.setSelected(true); } catch(Exception e){}
        if(!database.saveDesignSession(designName, selectedName, null)){return;}
        savedDesignSessions.addElement(designName);
    }//GEN-LAST:event_newDesignJMenuItemActionPerformed

    private void newMonitorJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMonitorJMenuItemActionPerformed
        // Get Full Api Key
        String apiKey = JOptionPane.showInputDialog("Please enter your Full Api key");
        if(apiKey==null || apiKey.isEmpty()){return;}

        // Get UserID
        int userID = Integer.parseInt(JOptionPane.showInputDialog("Please enter your userID"));
        if(String.valueOf(userID).trim().length()<=4){return;}

        // Get Characters Full Name
        String charName = JOptionPane.showInputDialog("Please enter your Characters name");
        if(charName==null || charName.isEmpty()){return;}

        // Get Chars ID
        CharactersResponse charsFound = EvePosMad_Api.getAccountCharacters(apiKey, userID);
        long charID = 0L;
        for (ApiCharacter charRecord : charsFound.getEveCharacters())
        {
            if (charRecord.getName().equalsIgnoreCase(charName)){ charID = charRecord.getCharacterID(); }
        }

        // Get POS List
        StarbaseListResponse posList = EvePosMad_Api.getStarbaseList(apiKey, charID, userID);
        // Display Results
        String posListed = "";
        for (ApiStarbase posRecord : posList.getStarbases())
        {
            posListed = posListed + posRecord + "\n";
        }

        // Allow User To Select POS To Monitor
        long moonID = Long.parseLong(JOptionPane.showInputDialog("Please enter the moonID of the POS you wish to monitor. \n\n" + posListed));
        if(moonID==0L){return;}

        // Get POS Detail
        long posID = 0L;
        for (ApiStarbase posRecord : posList.getStarbases())
        {
            if(posRecord.getMoonID()==moonID) { posID = posRecord.getItemID(); }
        }
        StarbaseDetailResponse posDetail = EvePosMad_Api.getStarbaseDetail(apiKey, charID, userID, posID);

        // Display Monitor Window
        dynamicJDesktopPane.add(new EvePosMad_Monitor(posDetail, posList, posID, this, apiKey, String.valueOf(charID), String.valueOf(userID), String.valueOf(posID), "", true), javax.swing.JLayeredPane.DEFAULT_LAYER);
    }//GEN-LAST:event_newMonitorJMenuItemActionPerformed

    private void loadSelectedViewJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadSelectedViewJButtonActionPerformed
        if((savedDesignsJList.isSelectionEmpty() && savedMonitorsJList.isSelectionEmpty()) ||
           (!savedDesignsJList.isSelectionEmpty() && !savedMonitorsJList.isSelectionEmpty()))
        { /* Do nothing, unwanted situation */ }
        else
        {
            if(!savedDesignsJList.isSelectionEmpty())
            {
                String designSelected = savedDesignsJList.getSelectedValue().toString();
                String[] controlTowerName = new String[1];
                ArrayList<String[]> moduleDetails = new ArrayList<String[]>();

                if(!database.loadDesignSession(designSelected, controlTowerName, moduleDetails)){return;}

                for(JInternalFrame frame : dynamicJDesktopPane.getAllFrames())
                {
                    if("Design Window".equalsIgnoreCase(frame.getTitle()))
                    {
                        EvePosMad_Design castFrame = (EvePosMad_Design) frame;
                        if(castFrame.designName.equals(designSelected))
                        {
                            JOptionPane.showMessageDialog(mainPanel,"This Design Has Already Been Loaded",
                            "Failed Desin Load", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                EvePosMad_Design frame = new EvePosMad_Design(designSelected, controlTowerName[0]);

                if(controlTowerName==null){return;}

                if(moduleDetails!=null || !moduleDetails.isEmpty())
                {
                    for(String[] module : moduleDetails)
                    {
                        for(int i=0; i<Integer.parseInt(module[1]); i++)
                        {
                            frame.addModuleToTower(module[0]);
                        }
                    }
                }

                dynamicJDesktopPane.add(frame, javax.swing.JLayeredPane.DEFAULT_LAYER);
                try { frame.setSelected(true); } catch(Exception e){}
            }
            else
            {
                String monitorSelected = savedMonitorsJList.getSelectedValue().toString();
                String[] apiDetails = new String[]{"","","","",monitorSelected};
                StarbaseDetailResponse posDetail = new StarbaseDetailResponse();
                StarbaseListResponse posList = new StarbaseListResponse();

                if(!database.loadMonitorSession(apiDetails, posDetail, posList))
                {
                    JOptionPane.showMessageDialog(mainPanel,"Failed To Load Saved Monitor Session",
                    "Failed Session Load", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for(JInternalFrame frame : dynamicJDesktopPane.getAllFrames())
                {
                    if("Monitor Window".equalsIgnoreCase(frame.getTitle()))
                    {
                        EvePosMad_Monitor castFrame = (EvePosMad_Monitor) frame;
                        if(castFrame.getID().equals(apiDetails[4]))
                        {
                            JOptionPane.showMessageDialog(mainPanel,"This Session Has Already Been Loaded",
                            "Failed Session Load", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                EvePosMad_Monitor frame = new EvePosMad_Monitor
                (
                    posDetail,                      //StarbaseDetailResponse
                    posList,                        //StarbaseListResponse
                    Long.parseLong(apiDetails[3]),  //posItemID LONG
                    this,
                    apiDetails[0],                  //apiKey
                    apiDetails[1],                  //userID
                    apiDetails[2],                  //characterID
                    apiDetails[3],                  //structureID STRING
                    apiDetails[4],                  //Monitor Name
                    false                           //save button state
                );
                dynamicJDesktopPane.add(frame, javax.swing.JLayeredPane.DEFAULT_LAYER);
                try { frame.setSelected(true); } catch(Exception e){}
            }
        }
    }//GEN-LAST:event_loadSelectedViewJButtonActionPerformed

    private void deleteSelectedJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSelectedJButtonActionPerformed

        //Get name of monitor to remove
        if((savedDesignsJList.isSelectionEmpty() && savedMonitorsJList.isSelectionEmpty()) ||
           (!savedDesignsJList.isSelectionEmpty() && !savedMonitorsJList.isSelectionEmpty()))
        { return; }
        if(!savedDesignsJList.isSelectionEmpty())
        {
            String designSelected = savedDesignsJList.getSelectedValue().toString();

            //Confirm
            int result = JOptionPane.showConfirmDialog(mainPanel,
            "Do you realy want to remove '"+designSelected+"' ?", "Are You Sure?",
            JOptionPane.YES_NO_OPTION);

            if(JOptionPane.NO_OPTION == result){return;}

            //Delete Database Eentry
            if(!database.deleteDesignSession(designSelected)){ return; }

            //Remove from JList
            if(!savedDesignSessions.removeElement(designSelected)){ return; }

            //Remove JInternalFrame
            for(JInternalFrame frame : dynamicJDesktopPane.getAllFrames())
            {
                if("Design Window".equalsIgnoreCase(frame.getTitle()))
                {
                    EvePosMad_Design castFrame = (EvePosMad_Design) frame;
                    if(castFrame.designName.equals(designSelected))
                    {
                        dynamicJDesktopPane.remove(frame);
                        dynamicJDesktopPane.validate();
                        dynamicJDesktopPane.repaint();
                    }
                }
            }

            //Confirmation Window
            JOptionPane.showMessageDialog(mainPanel,"You have successully removed the saved design: '"+designSelected+"'",
            "Successfully Removed Design", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            String monitorSelected = savedMonitorsJList.getSelectedValue().toString();

            //Confirm
            int result = JOptionPane.showConfirmDialog(mainPanel,
            "Do you realy want to remove '"+monitorSelected+"' ?", "Are You Sure?",
            JOptionPane.YES_NO_OPTION);

            if(JOptionPane.NO_OPTION == result){return;}

            //Delete Database Eentry
            if(!database.deleteSavedMonitorSession(monitorSelected)){ return; }

            //Remove from JList
            if(!savedMonitorSessions.removeElement(monitorSelected)){ return; }

            //Remove JInternalFrame
            for(JInternalFrame frame : dynamicJDesktopPane.getAllFrames())
            {
                if("Monitor Window".equalsIgnoreCase(frame.getTitle()))
                {
                    EvePosMad_Monitor castFrame = (EvePosMad_Monitor) frame;
                    if(castFrame.getID().equals(monitorSelected))
                    {
                        dynamicJDesktopPane.remove(frame);
                        dynamicJDesktopPane.validate();
                        dynamicJDesktopPane.repaint();
                    }
                }
            }

            //Confirmation Window
            JOptionPane.showMessageDialog(mainPanel,"You have successully removed the saved monitor session: '"+monitorSelected+"'",
            "Successfully Removed Monitor Session", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteSelectedJButtonActionPerformed

    private void renameSelectedJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameSelectedJButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_renameSelectedJButtonActionPerformed

    private void closeModuleSearchViewJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeModuleSearchViewJButtonActionPerformed
        eastJPanel.setVisible(false);
    }//GEN-LAST:event_closeModuleSearchViewJButtonActionPerformed

    private void viewFittingPanelJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewFittingPanelJMenuItemActionPerformed
        if(eastJPanel.isVisible()){eastJPanel.setVisible(false);}
        else{eastJPanel.setVisible(true);}
    }//GEN-LAST:event_viewFittingPanelJMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void moduleSearchJTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_moduleSearchJTreeValueChanged
        DefaultMutableTreeNode selectedNode =
       (DefaultMutableTreeNode) moduleSearchJTree.getLastSelectedPathComponent();

        selectedModuleTableModel.setRowCount(0);
        if(selectedNode==null){ return; }
        if(selectedNode.isLeaf())
        {
            ArrayList<String[]> result = new ArrayList<String[]>();
            EvePosMad_DesignTreeObject category = 
                    (EvePosMad_DesignTreeObject) selectedNode.getUserObject();
            for(long categoryID : category.categoryIDs)
            {
                result.addAll(database.getModuleList
                (
                    selectedNode.getParent().toString(),
                    category.categoryName,
                    String.valueOf(categoryID)
                ));
            }
            for(String[] rowEntry : result)
            {
                selectedModuleTableModel.addRow(rowEntry);
            }
        }
    }//GEN-LAST:event_moduleSearchJTreeValueChanged

    private void moduleSearchBoxJTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_moduleSearchBoxJTextFieldFocusGained
        if("< Enter Text To Search >".equals(moduleSearchBoxJTextField.getText()))
        {
            moduleSearchBoxJTextField.setText("");
            moduleSearchBoxJTextField.setForeground(new Color(0,0,0));
        }
    }//GEN-LAST:event_moduleSearchBoxJTextFieldFocusGained

    private void moduleSearchBoxJTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_moduleSearchBoxJTextFieldFocusLost
        if(moduleSearchBoxJTextField.getText().isEmpty())
        {
            moduleSearchBoxJTextField.setText("< Enter Text To Search >");
            moduleSearchBoxJTextField.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_moduleSearchBoxJTextFieldFocusLost

    private void searchJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchJButtonActionPerformed
        String searchText = moduleSearchBoxJTextField.getText();
        long[] arrayOfCategories = new long[]{449L, 430L, 417L, 426L, 837L,
        440L, 439L, 441L, 443L, 471L, 363L, 444L, 397L, 413L, 416L, 311L,
        438L, 404L, 838L, 839L, 707L};

        selectedModuleTableModel.setRowCount(0);
        if(searchText==null || "< Enter Text To Search >".equals(searchText))
        { return; }
        if(searchText.isEmpty()){ return; }
        else
        {
            ArrayList<String[]> result = new ArrayList<String[]>();

            result.addAll(database.getModuleList
            (
                "Search",
                searchText,
                arrayOfCategories
            ));

            for(String[] rowEntry : result)
            {
                selectedModuleTableModel.addRow(rowEntry);
            }
        }
    }//GEN-LAST:event_searchJButtonActionPerformed

    private void moduleSearchBoxJTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_moduleSearchBoxJTextFieldKeyReleased
        if("Enter".equals(KeyEvent.getKeyText(evt.getKeyCode())))
        {
            searchJButtonActionPerformed(null);
        }
    }//GEN-LAST:event_moduleSearchBoxJTextFieldKeyReleased

    private void viewSavedSessionsViewJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewSavedSessionsViewJMenuItemActionPerformed
        if(westJPanel.isVisible()){westJPanel.setVisible(false);}
        else{westJPanel.setVisible(true);}
    }//GEN-LAST:event_viewSavedSessionsViewJMenuItemActionPerformed

    private void moduleListJTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moduleListJTableMouseReleased
        if (evt.getComponent().isEnabled() && evt.getButton() ==
                    MouseEvent.BUTTON1 && evt.getClickCount() == 2)
        {
            int row = moduleListJTable.rowAtPoint(evt.getPoint());
            if(row==-1 || dynamicJDesktopPane.getSelectedFrame()==null){return;}

            if("Design Window".equalsIgnoreCase(dynamicJDesktopPane.getSelectedFrame().getTitle()))
            {
                EvePosMad_Design currentFrame = (EvePosMad_Design) dynamicJDesktopPane.getSelectedFrame();
                currentFrame.addModuleToTower(moduleListJTable.getModel().getValueAt(row, 0).toString());
                //currentFrame.addModuleToTower("Jump Bridge");
            }
        }
    }//GEN-LAST:event_moduleListJTableMouseReleased

    private void wikiPagesJMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wikiPagesJMenuItemActionPerformed
        if(Desktop.isDesktopSupported())
        {
            try
            {
                Desktop.getDesktop().browse(new URI("https://code.google.com/p/eve-pos-mad/w/list"));
            }
            catch (Exception e){}
        }
    }//GEN-LAST:event_wikiPagesJMenuItemActionPerformed

    private DefaultTreeModel generateDesignTree()
    {
        //EvePosMad_DesignTreeObject
        DefaultMutableTreeNode rootNode, weaponsNode, ewNode, defenceNode,
        industryNode, miscellaneousNode, hybridBatteriesNode, laserBatteriesNode,
        missileBatteriesNode, projectileBatteriesNode, blastersNode, railgunsNode,
        beamLasersNode, pulseLasersNode, cruiseNode, torpedoNode, citadelNode,
        artilleryNode, autoCannonsNode;

        blastersNode = new DefaultMutableTreeNode("Blaster");
        blastersNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Small", new long[]{449L})));
        blastersNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Medium", new long[]{449L})));
        blastersNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Large", new long[]{449L})));

        railgunsNode = new DefaultMutableTreeNode("Railgun");
        railgunsNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Small", new long[]{449L})));
        railgunsNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Medium", new long[]{449L})));
        railgunsNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Large", new long[]{449L})));

        hybridBatteriesNode = new DefaultMutableTreeNode("Hybrid Batteries");
        hybridBatteriesNode.add(blastersNode);
        hybridBatteriesNode.add(railgunsNode);

        beamLasersNode = new DefaultMutableTreeNode("Beam Laser");
        beamLasersNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Small", new long[]{430L})));
        beamLasersNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Medium", new long[]{430L})));
        beamLasersNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Large", new long[]{430L})));

        pulseLasersNode = new DefaultMutableTreeNode("Pulse Laser");
        pulseLasersNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Small", new long[]{430L})));
        pulseLasersNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Medium", new long[]{430L})));
        pulseLasersNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Large", new long[]{430L})));

        laserBatteriesNode = new DefaultMutableTreeNode("Laser Batteries");
        laserBatteriesNode.add(beamLasersNode);
        laserBatteriesNode.add(pulseLasersNode);

        cruiseNode = new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Cruise Missile", new long[]{417L}));
        torpedoNode = new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Torpedo", new long[]{417L}));
        citadelNode = new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Citadel Torpedo", new long[]{417L}));

        missileBatteriesNode = new DefaultMutableTreeNode("Missile Batteries");
        missileBatteriesNode.add(cruiseNode);
        missileBatteriesNode.add(torpedoNode);
        missileBatteriesNode.add(citadelNode);

        artilleryNode = new DefaultMutableTreeNode("Artillery");
        artilleryNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Small", new long[]{426L})));
        artilleryNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Medium", new long[]{426L})));
        artilleryNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Large", new long[]{426L})));

        autoCannonsNode = new DefaultMutableTreeNode("AutoCannon");
        autoCannonsNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Small", new long[]{426L})));
        autoCannonsNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Medium", new long[]{426L})));
        autoCannonsNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Large", new long[]{426L})));

        projectileBatteriesNode = new DefaultMutableTreeNode("Projectile Batteries");
        projectileBatteriesNode.add(artilleryNode);
        projectileBatteriesNode.add(autoCannonsNode);

        weaponsNode = new DefaultMutableTreeNode("Weapons");
        weaponsNode.add(hybridBatteriesNode);
        weaponsNode.add(laserBatteriesNode);
        weaponsNode.add(missileBatteriesNode);
        weaponsNode.add(projectileBatteriesNode);

        ewNode = new DefaultMutableTreeNode("Electronic Warfare");
        ewNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Energy Neutralizing Battery", new long[]{837L})));
        ewNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Sensor Dampening Battery", new long[]{440L})));
        ewNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Sensor Jamming Battery", new long[]{439L})));
        ewNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Stasis Webification Battery", new long[]{441L})));
        ewNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Warp Inhibitor Battery", new long[]{443L})));

        defenceNode = new DefaultMutableTreeNode("Defence & Storage");
        defenceNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Hanger Arrays", new long[]{471L, 363L})));
        defenceNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Shield Hardner Arrays", new long[]{444L})));

        industryNode = new DefaultMutableTreeNode("Industry");
        industryNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Assembly Arrays", new long[]{397L})));
        industryNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Mobile Laboratories", new long[]{413L})));
        industryNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Moon Harvesting Arrays", new long[]{416L})));
        industryNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Refining Arrays", new long[]{311L})));
        industryNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Reactor Arrays", new long[]{438L})));
        industryNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Silos", new long[]{404L})));

        miscellaneousNode = new DefaultMutableTreeNode("Miscellaneous");
        miscellaneousNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Cynosural Arrays", new long[]{838L, 839L})));
        miscellaneousNode.add(new DefaultMutableTreeNode(new EvePosMad_DesignTreeObject("Jump Bridge Array", new long[]{707L})));

        rootNode = new DefaultMutableTreeNode("Player Owned Structre Modules");
        rootNode.add(weaponsNode);
        rootNode.add(ewNode);
        rootNode.add(defenceNode);
        rootNode.add(industryNode);
        rootNode.add(miscellaneousNode);
        return new DefaultTreeModel(rootNode);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonJPanel;
    private javax.swing.JPanel centreJPanel;
    private javax.swing.JMenuItem checkForUpdatesJMenuItem;
    private javax.swing.JButton closeModuleSearchViewJButton;
    private javax.swing.JButton deleteSelectedJButton;
    private javax.swing.JDesktopPane dynamicJDesktopPane;
    private javax.swing.JPanel eastJPanel;
    private javax.swing.JMenuItem editApiDetailsJMenuItem;
    private javax.swing.JMenuItem editDesignSettingsJMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem editMonitorSettingsJMenuItem;
    private javax.swing.JMenuItem exportPrefsJMenuItem;
    private javax.swing.JMenuItem faqJMenuItem;
    private javax.swing.JPopupMenu.Separator firstJSeparator;
    private javax.swing.JPopupMenu.Separator fourthJSeparator;
    private javax.swing.JMenuItem importPrefsJMenuItem;
    private javax.swing.JButton loadSelectedViewJButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JScrollPane moduleListJScrollPane;
    private javax.swing.JTable moduleListJTable;
    private javax.swing.JTextField moduleSearchBoxJTextField;
    private javax.swing.JScrollPane moduleSearchJScrollPane;
    private javax.swing.JTree moduleSearchJTree;
    private javax.swing.JSplitPane moduleSelectionJSplitPane;
    private javax.swing.JMenuItem newDesignJMenuItem;
    private javax.swing.JMenuItem newMonitorJMenuItem;
    private javax.swing.JMenuItem preferencesJMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton renameSelectedJButton;
    private javax.swing.JButton saveAllViewsJButton;
    private javax.swing.JLabel savedDesignsJLabel;
    private javax.swing.JList savedDesignsJList;
    private javax.swing.JPanel savedDesignsJPanel;
    private javax.swing.JScrollPane savedDesignsJScrollPane;
    private javax.swing.JLabel savedMonitorsJLabel;
    private javax.swing.JList savedMonitorsJList;
    private javax.swing.JPanel savedMonitorsJPanel;
    private javax.swing.JScrollPane savedMonitorsJScrollPane;
    private javax.swing.JSplitPane savedSessionsJSplitPane;
    private javax.swing.JButton searchJButton;
    private javax.swing.JPanel searchJPanel;
    private javax.swing.JPopupMenu.Separator secondJSeparator;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JPopupMenu.Separator thirdJSeparator;
    private javax.swing.JMenuItem viewFittingPanelJMenuItem;
    private javax.swing.JMenu viewMenu;
    private javax.swing.JMenuItem viewSavedSessionsViewJMenuItem;
    private javax.swing.JPanel westJPanel;
    private javax.swing.JMenuItem wikiPagesJMenuItem;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    private EvePosMad_Database database;
    public DefaultListModel savedMonitorSessions, savedDesignSessions;
    public DefaultTableModel selectedModuleTableModel;
}