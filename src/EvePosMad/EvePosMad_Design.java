package eveposmad;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Collections;
import java.util.ArrayList;
import PosObjects.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import static eveposmad.EvePosMad_Database.*;
/**
 * @author James Bacon
 */
public class EvePosMad_Design extends javax.swing.JInternalFrame {

    public EvePosMad_Design(String designName, String towerName){
        this.designName = designName;
        database = new EvePosMad_Database();
        controlTower = database.getControlTower(towerName);
        if(controlTower==null){return;}
        defenceAndStorageModel = new DefaultListModel();
        electronicWarefareModel = new DefaultListModel();
        offensiveModel = new DefaultListModel();
        industryModel = new DefaultListModel();
        miscellaneousModel = new DefaultListModel();
        initComponents();
        setupDragAndDrop();
        towerNameJTextField.setColumns(18);
    }

    private String getTowerName(){
        String towerName = controlTower.getName();
        if(towerName.contains("Small"))
        {
            return towerName.replaceFirst(" Small", "");
        }
        else if(towerName.contains("Medium"))
        {
            return towerName.replaceFirst(" Medium", "");
        }
        else
        {
            return controlTower.getName();
        }
    }

    private String getTowerSize(){
        if(controlTower.getName().contains("Small")){return "Small";}
        else if(controlTower.getName().contains("Medium")){return "Medium";}
        else { return "Large"; }
    }

    private void setupDragAndDrop(){
        mainJPanel.setTransferHandler(new TransferHandler(){
            @Override public boolean canImport(TransferHandler.TransferSupport info)
            {
                if(!info.isDataFlavorSupported(DataFlavor.stringFlavor)){return false;}
                return true;
            }

            @Override public boolean importData(TransferHandler.TransferSupport info)
            {
                if(!info.isDrop()){return false;}
                if(!info.isDataFlavorSupported(DataFlavor.stringFlavor)){return false;}
                Transferable t = info.getTransferable();
                String data;
                try
                {
                    data = (String) t.getTransferData(DataFlavor.stringFlavor);
                }
                catch(Exception e){ return false; }
                addModuleToTower(data);
                updateStats();
                return true;
            }

            @Override public int getSourceActions(JComponent c) { return COPY; }
        });
    }

    protected void addModuleToTower(String moduleName){
        if(moduleName==null || moduleName.isEmpty()){return;}
        while(true)
        {
            if(moduleName.contains("Refining") && moduleName.contains("Array"))
            { 
              industryModel.addElement(database.getPosStructure(moduleName, STRUCTURE_REFINER));
              break;
            }

            if((moduleName.contains("Maintenance") || moduleName.contains("Hangar"))
             && moduleName.contains("Array"))
            {
                defenceAndStorageModel.addElement(database.getPosStructure(moduleName, STRUCTURE_CORP_STORAGE));
                break;
            }

            if((moduleName.contains("Assembly") && moduleName.contains("Array"))
             || moduleName.contains("Drug Lab"))
            {
              industryModel.addElement(database.getPosStructure(moduleName, STRUCTURE_ASSEMBLY_ARRAY));
              break;
            }

            if(moduleName.contains("Silo") || moduleName.contains("Coupling Array")
            || moduleName.contains("General Storage"))
            {
              industryModel.addElement(database.getPosStructure(moduleName, STRUCTURE_SILO));
              break;
            }

            if(moduleName.contains("Laboratory"))
            {
              industryModel.addElement(database.getPosStructure(moduleName, STRUCTURE_MOBILE_LAB));
              break;
            }

            if(moduleName.contains("Moon") && moduleName.contains("Harvesting"))
            {
              industryModel.addElement(database.getPosStructure(moduleName, STRUCTURE_MOON_MINER));
              break;
            }

            if((moduleName.contains("Cruise") || moduleName.contains("Torpedo"))
             && moduleName.contains("Battery"))
            {
              offensiveModel.addElement(database.getPosStructure(moduleName, STRUCTURE_MISSILE_BATTERY));
              break;
            }

            if((moduleName.contains("Artillery") || moduleName.contains("AutoCannon")
             || moduleName.contains("Beam Laser") || moduleName.contains("Blaster")
             || moduleName.contains("Railgun") || moduleName.contains("Pulse Laser"))
             && moduleName.contains("Battery"))
            {
              offensiveModel.addElement(database.getPosStructure(moduleName, STRUCTURE_TURRET_BATTERY));
              break;
            }

            if(moduleName.contains("Reactor") && moduleName.contains("Array"))
            {
              industryModel.addElement(database.getPosStructure(moduleName, STRUCTURE_REACTOR));
              break;
            }

            if((moduleName.contains("Ion Field")
             || moduleName.contains("Phase Inversion")
             || moduleName.contains("Spatial Destabilization")
             || moduleName.contains("White Noise"))
             && moduleName.contains("Battery"))
            {
              electronicWarefareModel.addElement(database.getPosStructure(moduleName, STRUCTURE_JAMMER));
              break;
            }

            if(moduleName.contains("Dampening") && moduleName.contains("Battery"))
            {
              electronicWarefareModel.addElement(database.getPosStructure(moduleName, STRUCTURE_SENSOR_DAMP));
              break;
            }

            if(moduleName.contains("Stasis") && moduleName.contains("Webification"))
            {
              electronicWarefareModel.addElement(database.getPosStructure(moduleName, STRUCTURE_STASIS_WEB));
              break;
            }

            if(moduleName.contains("Warp") && moduleName.contains("Battery"))
            { 
                electronicWarefareModel.addElement(database.getPosStructure(moduleName, STRUCTURE_WARP_INHIBITOR));
                break;
            }

            if((moduleName.contains("Ballistic") || moduleName.contains("Heat")
             || moduleName.contains("Photon") || moduleName.contains("Explosion"))
             && moduleName.contains("Array"))
            {
                defenceAndStorageModel.addElement(database.getPosStructure(moduleName, STRUCTURE_SHIELD_HARDNER));
                break;
            }

            if(moduleName.contains("Jump Bridge"))
            {
              miscellaneousModel.addElement(database.getPosStructure(moduleName, STRUCTURE_JUMP_BRIDGE));
              break;
            }

            if(moduleName.contains("Neutralizing") && moduleName.contains("Battery"))
            {
              electronicWarefareModel.addElement(database.getPosStructure(moduleName, STRUCTURE_ENERGY_NEUT));
              break;
            }

            if(moduleName.contains("Cynosural"))
            {
              miscellaneousModel.addElement(database.getPosStructure(moduleName, STRUCTURE_CYNO_TOOL));
              break;
            }

            //else
            break;
        }
        updateStats();
    }

    private String[] calculateCurrentCpuAndPower(){
        double cpu=0, powergrid=0;

        ArrayList<Object> moduleList = new ArrayList<Object>();
        moduleList.addAll(Arrays.asList(defenceAndStorageModel.toArray()));
        moduleList.addAll(Arrays.asList(electronicWarefareModel.toArray()));
        moduleList.addAll(Arrays.asList(offensiveModel.toArray()));
        moduleList.addAll(Arrays.asList(industryModel.toArray()));
        moduleList.addAll(Arrays.asList(miscellaneousModel.toArray()));

        for(Object module : moduleList)
        {
            cpu = cpu + ((PosModule) module).getRequiredCPU();
            powergrid = powergrid + ((PosModule) module).getRequiredPowerGrid();
        }
        return new String[]{String.valueOf((int) cpu), String.valueOf((int) powergrid)};
    }

    private int[] calculateCurrentMassAndVolumne(){
        int mass=0, volumne=0;

        ArrayList<Object> moduleList = new ArrayList<Object>();
        moduleList.addAll(Arrays.asList(defenceAndStorageModel.toArray()));
        moduleList.addAll(Arrays.asList(electronicWarefareModel.toArray()));
        moduleList.addAll(Arrays.asList(offensiveModel.toArray()));
        moduleList.addAll(Arrays.asList(industryModel.toArray()));
        moduleList.addAll(Arrays.asList(miscellaneousModel.toArray()));

        for(Object module : moduleList)
        {
            mass += ((PosModule) module).getMass();
            volumne += (int) ((PosModule) module).getVolume();
        }

        mass += controlTower.getMass();
        volumne += (int) controlTower.getVolume();

        return new int[]{mass,volumne};
    }

    private int countNumberOfModules(){
        int count = 0;
        count += defenceAndStorageModel.size();
        count += electronicWarefareModel.size();
        count += offensiveModel.size();
        count += industryModel.size();
        count += miscellaneousModel.size();
        return count;
    }

    private void updateStats(){
        int cpu = Integer.parseInt(calculateCurrentCpuAndPower()[0]);
        cpuJProgressBar.setValue(cpu);
        cpuJProgressBar.setToolTipText(cpu+" Used / "+((int) controlTower.getCpu())+" Tower Limit");

        int power = Integer.parseInt(calculateCurrentCpuAndPower()[1]);
        powergridJProgressBar.setValue(power);
        powergridJProgressBar.setToolTipText(power+" Used / "+((int) controlTower.getPowerGrid())+" Tower Limit");

        //formatter.format()
        countOfModulesJTextField.setText(formatter.format(countNumberOfModules()));
        totalMassJTextField.setText(formatter.format(calculateCurrentMassAndVolumne()[0])+" kg");
        totalVolumneJTextField.setText(formatter.format(calculateCurrentMassAndVolumne()[1])+" m3");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainJPanel = new javax.swing.JPanel();
        centerJPanel = new javax.swing.JPanel();
        posModulesJScrollPane = new javax.swing.JScrollPane();
        posModulesJPanel = new javax.swing.JPanel();
        offensiveJPanel = new javax.swing.JPanel();
        offensiveJScrollPane = new javax.swing.JScrollPane();
        offensiveJList = new javax.swing.JList();
        electronicWarefareJPanel = new javax.swing.JPanel();
        electronicWarefareJScrollPane = new javax.swing.JScrollPane();
        electronicWarefareJList = new javax.swing.JList();
        defenceAndStorageJPanel = new javax.swing.JPanel();
        defenceAndStorageJScrollPane = new javax.swing.JScrollPane();
        defenceAndStorageJList = new javax.swing.JList();
        industryJPanel = new javax.swing.JPanel();
        industryJScrollPane = new javax.swing.JScrollPane();
        industryJList = new javax.swing.JList();
        miscellaneousJPanel = new javax.swing.JPanel();
        miscellaneousJScrollPane = new javax.swing.JScrollPane();
        miscellaneousJList = new javax.swing.JList();
        eastJScrollPane = new javax.swing.JScrollPane();
        eastJPanel = new javax.swing.JPanel();
        posStatisticsJPanel = new javax.swing.JPanel();
        towerNameJLabel = new javax.swing.JLabel();
        towerNameJTextField = new javax.swing.JTextField();
        towerSizeJLabel = new javax.swing.JLabel();
        towerSizeJTextField = new javax.swing.JTextField();
        secondJSeparator = new javax.swing.JSeparator();
        designNameJLabel = new javax.swing.JLabel();
        designNameJTextField = new javax.swing.JTextField();
        thirdJSeparator = new javax.swing.JSeparator();
        cpuJProgressBar = new javax.swing.JProgressBar();
        cpuJLabel = new javax.swing.JLabel();
        powergirdJLabel = new javax.swing.JLabel();
        powergridJProgressBar = new javax.swing.JProgressBar();
        fourthJSeparator = new javax.swing.JSeparator();
        shieldHPJLabel = new javax.swing.JLabel();
        shieldHPJTextField = new javax.swing.JTextField();
        armorHPJLabel = new javax.swing.JLabel();
        armorHPJTextField = new javax.swing.JTextField();
        structureHPJLabel = new javax.swing.JLabel();
        structureHPJTextField = new javax.swing.JTextField();
        fifthJSeparator = new javax.swing.JSeparator();
        countOfModulesJLabel = new javax.swing.JLabel();
        countOfModulesJTextField = new javax.swing.JTextField();
        totalMassJLabel = new javax.swing.JLabel();
        totalMassJTextField = new javax.swing.JTextField();
        totalVolumneJLabel = new javax.swing.JLabel();
        totalVolumneJTextField = new javax.swing.JTextField();
        buttonOuterJPanel = new javax.swing.JPanel();
        buttonInnerJPanel = new javax.swing.JPanel();
        saveChangesJButton = new javax.swing.JButton();
        cancelChangesJButton = new javax.swing.JButton();
        firstJSeparator = new javax.swing.JSeparator();
        deselectAllChangesJButton = new javax.swing.JButton();
        selectAllChangesJButton = new javax.swing.JButton();
        removeSelectedChangesJButton = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(EvePosMad_Design.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setVisible(true);

        mainJPanel.setName("mainJPanel"); // NOI18N
        mainJPanel.setLayout(new java.awt.BorderLayout());

        centerJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 0));
        centerJPanel.setName("centerJPanel"); // NOI18N
        centerJPanel.setLayout(new java.awt.BorderLayout());

        posModulesJScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(resourceMap.getColor("posModulesJScrollPane.border.border.lineColor"), 1, true), resourceMap.getString("posModulesJScrollPane.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("posModulesJScrollPane.border.titleFont"), resourceMap.getColor("posModulesJScrollPane.border.titleColor"))); // NOI18N
        posModulesJScrollPane.setName("posModulesJScrollPane"); // NOI18N

        posModulesJPanel.setName("posModulesJPanel"); // NOI18N
        posModulesJPanel.setLayout(new javax.swing.BoxLayout(posModulesJPanel, javax.swing.BoxLayout.PAGE_AXIS));

        offensiveJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2), new javax.swing.border.LineBorder(resourceMap.getColor("miscellaneousJPanel.border.border.insideBorder.lineColor"), 1, true)), resourceMap.getString("offensiveJPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), resourceMap.getColor("electronicWarefareJPanel.border.titleColor"))); // NOI18N
        offensiveJPanel.setMinimumSize(new java.awt.Dimension(141, 51));
        offensiveJPanel.setName("offensiveJPanel"); // NOI18N
        offensiveJPanel.setPreferredSize(new java.awt.Dimension(185, 109));
        offensiveJPanel.setLayout(new java.awt.BorderLayout());

        offensiveJScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 4, 3));
        offensiveJScrollPane.setMaximumSize(new java.awt.Dimension(175, 5));
        offensiveJScrollPane.setMinimumSize(new java.awt.Dimension(175, 5));
        offensiveJScrollPane.setName("offensiveJScrollPane"); // NOI18N
        offensiveJScrollPane.setPreferredSize(new java.awt.Dimension(175, 5));

        offensiveJList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        offensiveJList.setModel(offensiveModel);
        offensiveJList.setDropMode(javax.swing.DropMode.ON);
        offensiveJList.setMaximumSize(null);
        offensiveJList.setMinimumSize(null);
        offensiveJList.setName("offensiveJList"); // NOI18N
        offensiveJList.setPreferredSize(null);
        offensiveJList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                offensiveJListKeyReleased(evt);
            }
        });
        offensiveJScrollPane.setViewportView(offensiveJList);

        offensiveJPanel.add(offensiveJScrollPane, java.awt.BorderLayout.CENTER);

        posModulesJPanel.add(offensiveJPanel);
        offensiveJPanel.getAccessibleContext().setAccessibleName(resourceMap.getString("offensiveJPanel.AccessibleContext.accessibleName")); // NOI18N

        electronicWarefareJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2), new javax.swing.border.LineBorder(resourceMap.getColor("miscellaneousJPanel.border.border.insideBorder.lineColor"), 1, true)), resourceMap.getString("electronicWarefareJPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), resourceMap.getColor("electronicWarefareJPanel.border.titleColor"))); // NOI18N
        electronicWarefareJPanel.setMinimumSize(new java.awt.Dimension(141, 51));
        electronicWarefareJPanel.setName("electronicWarefareJPanel"); // NOI18N
        electronicWarefareJPanel.setPreferredSize(new java.awt.Dimension(185, 109));
        electronicWarefareJPanel.setLayout(new java.awt.BorderLayout());

        electronicWarefareJScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 4, 3));
        electronicWarefareJScrollPane.setMaximumSize(new java.awt.Dimension(175, 5));
        electronicWarefareJScrollPane.setMinimumSize(new java.awt.Dimension(175, 5));
        electronicWarefareJScrollPane.setName("electronicWarefareJScrollPane"); // NOI18N
        electronicWarefareJScrollPane.setPreferredSize(new java.awt.Dimension(175, 5));

        electronicWarefareJList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        electronicWarefareJList.setModel(electronicWarefareModel);
        electronicWarefareJList.setDropMode(javax.swing.DropMode.ON);
        electronicWarefareJList.setMaximumSize(null);
        electronicWarefareJList.setMinimumSize(null);
        electronicWarefareJList.setName("electronicWarefareJList"); // NOI18N
        electronicWarefareJList.setPreferredSize(null);
        electronicWarefareJList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                electronicWarefareJListKeyReleased(evt);
            }
        });
        electronicWarefareJScrollPane.setViewportView(electronicWarefareJList);

        electronicWarefareJPanel.add(electronicWarefareJScrollPane, java.awt.BorderLayout.CENTER);

        posModulesJPanel.add(electronicWarefareJPanel);

        defenceAndStorageJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2), new javax.swing.border.LineBorder(resourceMap.getColor("defenceAndStorageJPanel.border.border.insideBorder.lineColor"), 1, true)), resourceMap.getString("defenceAndStorageJPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), resourceMap.getColor("defenceAndStorageJPanel.border.titleColor"))); // NOI18N
        defenceAndStorageJPanel.setMinimumSize(new java.awt.Dimension(141, 51));
        defenceAndStorageJPanel.setName("defenceAndStorageJPanel"); // NOI18N
        defenceAndStorageJPanel.setPreferredSize(new java.awt.Dimension(185, 109));
        defenceAndStorageJPanel.setLayout(new java.awt.BorderLayout());

        defenceAndStorageJScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 4, 3));
        defenceAndStorageJScrollPane.setMaximumSize(new java.awt.Dimension(175, 5));
        defenceAndStorageJScrollPane.setMinimumSize(new java.awt.Dimension(175, 5));
        defenceAndStorageJScrollPane.setName("defenceAndStorageJScrollPane"); // NOI18N
        defenceAndStorageJScrollPane.setPreferredSize(new java.awt.Dimension(175, 5));

        defenceAndStorageJList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        defenceAndStorageJList.setModel(defenceAndStorageModel);
        defenceAndStorageJList.setDropMode(javax.swing.DropMode.ON);
        defenceAndStorageJList.setMaximumSize(null);
        defenceAndStorageJList.setMinimumSize(null);
        defenceAndStorageJList.setName("defenceAndStorageJList"); // NOI18N
        defenceAndStorageJList.setPreferredSize(null);
        defenceAndStorageJList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                defenceAndStorageJListKeyReleased(evt);
            }
        });
        defenceAndStorageJScrollPane.setViewportView(defenceAndStorageJList);

        defenceAndStorageJPanel.add(defenceAndStorageJScrollPane, java.awt.BorderLayout.CENTER);

        posModulesJPanel.add(defenceAndStorageJPanel);

        industryJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2), new javax.swing.border.LineBorder(resourceMap.getColor("miscellaneousJPanel.border.border.insideBorder.lineColor"), 1, true)), resourceMap.getString("industryJPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), resourceMap.getColor("electronicWarefareJPanel.border.titleColor"))); // NOI18N
        industryJPanel.setMinimumSize(new java.awt.Dimension(141, 51));
        industryJPanel.setName("industryJPanel"); // NOI18N
        industryJPanel.setPreferredSize(new java.awt.Dimension(185, 109));
        industryJPanel.setLayout(new java.awt.BorderLayout());

        industryJScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 4, 3));
        industryJScrollPane.setMaximumSize(new java.awt.Dimension(175, 5));
        industryJScrollPane.setMinimumSize(new java.awt.Dimension(175, 5));
        industryJScrollPane.setName("industryJScrollPane"); // NOI18N
        industryJScrollPane.setPreferredSize(new java.awt.Dimension(175, 5));

        industryJList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        industryJList.setModel(industryModel);
        industryJList.setDropMode(javax.swing.DropMode.ON);
        industryJList.setMaximumSize(null);
        industryJList.setMinimumSize(null);
        industryJList.setName("industryJList"); // NOI18N
        industryJList.setPreferredSize(null);
        industryJList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                industryJListKeyTyped(evt);
            }
        });
        industryJScrollPane.setViewportView(industryJList);

        industryJPanel.add(industryJScrollPane, java.awt.BorderLayout.CENTER);

        posModulesJPanel.add(industryJPanel);

        miscellaneousJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2), new javax.swing.border.LineBorder(resourceMap.getColor("miscellaneousJPanel.border.border.insideBorder.lineColor"), 1, true)), resourceMap.getString("miscellaneousJPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), resourceMap.getColor("electronicWarefareJPanel.border.titleColor"))); // NOI18N
        miscellaneousJPanel.setMinimumSize(new java.awt.Dimension(141, 51));
        miscellaneousJPanel.setName("miscellaneousJPanel"); // NOI18N
        miscellaneousJPanel.setPreferredSize(new java.awt.Dimension(185, 109));
        miscellaneousJPanel.setLayout(new java.awt.BorderLayout());

        miscellaneousJScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 4, 3));
        miscellaneousJScrollPane.setMaximumSize(new java.awt.Dimension(175, 5));
        miscellaneousJScrollPane.setMinimumSize(new java.awt.Dimension(175, 5));
        miscellaneousJScrollPane.setName("miscellaneousJScrollPane"); // NOI18N
        miscellaneousJScrollPane.setPreferredSize(new java.awt.Dimension(175, 5));

        miscellaneousJList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        miscellaneousJList.setModel(miscellaneousModel);
        miscellaneousJList.setDropMode(javax.swing.DropMode.ON);
        miscellaneousJList.setMaximumSize(null);
        miscellaneousJList.setMinimumSize(null);
        miscellaneousJList.setName("miscellaneousJList"); // NOI18N
        miscellaneousJList.setPreferredSize(null);
        miscellaneousJList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                miscellaneousJListKeyReleased(evt);
            }
        });
        miscellaneousJScrollPane.setViewportView(miscellaneousJList);

        miscellaneousJPanel.add(miscellaneousJScrollPane, java.awt.BorderLayout.CENTER);

        posModulesJPanel.add(miscellaneousJPanel);

        posModulesJScrollPane.setViewportView(posModulesJPanel);

        centerJPanel.add(posModulesJScrollPane, java.awt.BorderLayout.CENTER);

        mainJPanel.add(centerJPanel, java.awt.BorderLayout.CENTER);

        eastJScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 0));
        eastJScrollPane.setName("eastJScrollPane"); // NOI18N

        eastJPanel.setMinimumSize(new java.awt.Dimension(246, 410));
        eastJPanel.setName("eastJPanel"); // NOI18N
        eastJPanel.setPreferredSize(new java.awt.Dimension(246, 418));
        eastJPanel.setLayout(new java.awt.BorderLayout());

        posStatisticsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(resourceMap.getColor("posStatisticsJPanel.border.border.lineColor"), 1, true), resourceMap.getString("posStatisticsJPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("posStatisticsJPanel.border.titleFont"), resourceMap.getColor("posStatisticsJPanel.border.titleColor"))); // NOI18N
        posStatisticsJPanel.setName("posStatisticsJPanel"); // NOI18N
        posStatisticsJPanel.setLayout(new java.awt.GridBagLayout());

        towerNameJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        towerNameJLabel.setText(resourceMap.getString("towerNameJLabel.text")); // NOI18N
        towerNameJLabel.setName("towerNameJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        posStatisticsJPanel.add(towerNameJLabel, gridBagConstraints);

        towerNameJTextField.setEditable(false);
        towerNameJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        towerNameJTextField.setText(this.getTowerName());
        towerNameJTextField.setMinimumSize(new java.awt.Dimension(150, 20));
        towerNameJTextField.setName("towerNameJTextField"); // NOI18N
        towerNameJTextField.setPreferredSize(new java.awt.Dimension(150, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        posStatisticsJPanel.add(towerNameJTextField, gridBagConstraints);

        towerSizeJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        towerSizeJLabel.setText(resourceMap.getString("towerSizeJLabel.text")); // NOI18N
        towerSizeJLabel.setName("towerSizeJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        posStatisticsJPanel.add(towerSizeJLabel, gridBagConstraints);

        towerSizeJTextField.setEditable(false);
        towerSizeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        towerSizeJTextField.setText(this.getTowerSize());
        towerSizeJTextField.setMaximumSize(null);
        towerSizeJTextField.setMinimumSize(null);
        towerSizeJTextField.setName("towerSizeJTextField"); // NOI18N
        towerSizeJTextField.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        posStatisticsJPanel.add(towerSizeJTextField, gridBagConstraints);

        secondJSeparator.setForeground(resourceMap.getColor("secondJSeparator.foreground")); // NOI18N
        secondJSeparator.setName("secondJSeparator"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        posStatisticsJPanel.add(secondJSeparator, gridBagConstraints);

        designNameJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        designNameJLabel.setText(resourceMap.getString("designNameJLabel.text")); // NOI18N
        designNameJLabel.setName("designNameJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        posStatisticsJPanel.add(designNameJLabel, gridBagConstraints);

        designNameJTextField.setEditable(false);
        designNameJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        designNameJTextField.setText(this.designName);
        designNameJTextField.setMaximumSize(null);
        designNameJTextField.setMinimumSize(null);
        designNameJTextField.setName("designNameJTextField"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        posStatisticsJPanel.add(designNameJTextField, gridBagConstraints);

        thirdJSeparator.setForeground(resourceMap.getColor("thirdJSeparator.foreground")); // NOI18N
        thirdJSeparator.setName("thirdJSeparator"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        posStatisticsJPanel.add(thirdJSeparator, gridBagConstraints);

        cpuJProgressBar.setMaximum((int) controlTower.getCpu());
        cpuJProgressBar.setName("cpuJProgressBar"); // NOI18N
        cpuJProgressBar.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        posStatisticsJPanel.add(cpuJProgressBar, gridBagConstraints);

        cpuJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cpuJLabel.setText(resourceMap.getString("cpuJLabel.text")); // NOI18N
        cpuJLabel.setName("cpuJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 4, 2);
        posStatisticsJPanel.add(cpuJLabel, gridBagConstraints);

        powergirdJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        powergirdJLabel.setText(resourceMap.getString("powergirdJLabel.text")); // NOI18N
        powergirdJLabel.setName("powergirdJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 2, 5, 2);
        posStatisticsJPanel.add(powergirdJLabel, gridBagConstraints);

        powergridJProgressBar.setMaximum((int) controlTower.getPowerGrid());
        powergridJProgressBar.setName("powergridJProgressBar"); // NOI18N
        powergridJProgressBar.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        posStatisticsJPanel.add(powergridJProgressBar, gridBagConstraints);

        fourthJSeparator.setForeground(resourceMap.getColor("fourthJSeparator.foreground")); // NOI18N
        fourthJSeparator.setName("fourthJSeparator"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        posStatisticsJPanel.add(fourthJSeparator, gridBagConstraints);

        shieldHPJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        shieldHPJLabel.setText(resourceMap.getString("shieldHPJLabel.text")); // NOI18N
        shieldHPJLabel.setName("shieldHPJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        posStatisticsJPanel.add(shieldHPJLabel, gridBagConstraints);

        shieldHPJTextField.setEditable(false);
        shieldHPJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        shieldHPJTextField.setText(formatter.format(controlTower.getShieldCapacity()));
        shieldHPJTextField.setMaximumSize(null);
        shieldHPJTextField.setMinimumSize(null);
        shieldHPJTextField.setName("shieldHPJTextField"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        posStatisticsJPanel.add(shieldHPJTextField, gridBagConstraints);

        armorHPJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        armorHPJLabel.setText(resourceMap.getString("armorHPJLabel.text")); // NOI18N
        armorHPJLabel.setName("armorHPJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        posStatisticsJPanel.add(armorHPJLabel, gridBagConstraints);

        armorHPJTextField.setEditable(false);
        armorHPJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        armorHPJTextField.setText(formatter.format(controlTower.getArmorHitpoints()));
        armorHPJTextField.setMaximumSize(null);
        armorHPJTextField.setMinimumSize(null);
        armorHPJTextField.setName("armorHPJTextField"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        posStatisticsJPanel.add(armorHPJTextField, gridBagConstraints);

        structureHPJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        structureHPJLabel.setText(resourceMap.getString("structureHPJLabel.text")); // NOI18N
        structureHPJLabel.setName("structureHPJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        posStatisticsJPanel.add(structureHPJLabel, gridBagConstraints);

        structureHPJTextField.setEditable(false);
        structureHPJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        structureHPJTextField.setText(formatter.format(controlTower.getStructureHitpoints()));
        structureHPJTextField.setMaximumSize(null);
        structureHPJTextField.setMinimumSize(null);
        structureHPJTextField.setName("structureHPJTextField"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        posStatisticsJPanel.add(structureHPJTextField, gridBagConstraints);

        fifthJSeparator.setForeground(resourceMap.getColor("fifthJSeparator.foreground")); // NOI18N
        fifthJSeparator.setName("fifthJSeparator"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        posStatisticsJPanel.add(fifthJSeparator, gridBagConstraints);

        countOfModulesJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        countOfModulesJLabel.setText(resourceMap.getString("countOfModulesJLabel.text")); // NOI18N
        countOfModulesJLabel.setName("countOfModulesJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        posStatisticsJPanel.add(countOfModulesJLabel, gridBagConstraints);

        countOfModulesJTextField.setEditable(false);
        countOfModulesJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        countOfModulesJTextField.setText(resourceMap.getString("countOfModulesJTextField.text")); // NOI18N
        countOfModulesJTextField.setMaximumSize(null);
        countOfModulesJTextField.setMinimumSize(null);
        countOfModulesJTextField.setName("countOfModulesJTextField"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        posStatisticsJPanel.add(countOfModulesJTextField, gridBagConstraints);

        totalMassJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalMassJLabel.setText(resourceMap.getString("totalMassJLabel.text")); // NOI18N
        totalMassJLabel.setToolTipText(resourceMap.getString("totalMassJLabel.toolTipText")); // NOI18N
        totalMassJLabel.setName("totalMassJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        posStatisticsJPanel.add(totalMassJLabel, gridBagConstraints);

        totalMassJTextField.setEditable(false);
        totalMassJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalMassJTextField.setText(resourceMap.getString("totalMassJTextField.text")); // NOI18N
        totalMassJTextField.setMaximumSize(null);
        totalMassJTextField.setMinimumSize(null);
        totalMassJTextField.setName("totalMassJTextField"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        posStatisticsJPanel.add(totalMassJTextField, gridBagConstraints);

        totalVolumneJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalVolumneJLabel.setText(resourceMap.getString("totalVolumneJLabel.text")); // NOI18N
        totalVolumneJLabel.setToolTipText(resourceMap.getString("totalVolumneJLabel.toolTipText")); // NOI18N
        totalVolumneJLabel.setName("totalVolumneJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        posStatisticsJPanel.add(totalVolumneJLabel, gridBagConstraints);

        totalVolumneJTextField.setEditable(false);
        totalVolumneJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        totalVolumneJTextField.setText(resourceMap.getString("totalVolumneJTextField.text")); // NOI18N
        totalVolumneJTextField.setMaximumSize(null);
        totalVolumneJTextField.setMinimumSize(null);
        totalVolumneJTextField.setName("totalVolumneJTextField"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        posStatisticsJPanel.add(totalVolumneJTextField, gridBagConstraints);

        eastJPanel.add(posStatisticsJPanel, java.awt.BorderLayout.NORTH);

        buttonOuterJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 1, 1, 1));
        buttonOuterJPanel.setName("buttonOuterJPanel"); // NOI18N
        buttonOuterJPanel.setLayout(new java.awt.BorderLayout());

        buttonInnerJPanel.setName("buttonInnerJPanel"); // NOI18N
        buttonInnerJPanel.setLayout(new java.awt.GridBagLayout());

        saveChangesJButton.setText(resourceMap.getString("saveChangesJButton.text")); // NOI18N
        saveChangesJButton.setMaximumSize(new java.awt.Dimension(90, 20));
        saveChangesJButton.setMinimumSize(new java.awt.Dimension(90, 20));
        saveChangesJButton.setName("saveChangesJButton"); // NOI18N
        saveChangesJButton.setPreferredSize(new java.awt.Dimension(90, 20));
        saveChangesJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveChangesJButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 5, 2);
        buttonInnerJPanel.add(saveChangesJButton, gridBagConstraints);

        cancelChangesJButton.setText(resourceMap.getString("cancelChangesJButton.text")); // NOI18N
        cancelChangesJButton.setMaximumSize(new java.awt.Dimension(90, 20));
        cancelChangesJButton.setMinimumSize(new java.awt.Dimension(90, 20));
        cancelChangesJButton.setName("cancelChangesJButton"); // NOI18N
        cancelChangesJButton.setPreferredSize(new java.awt.Dimension(90, 20));
        cancelChangesJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelChangesJButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 2, 5, 0);
        buttonInnerJPanel.add(cancelChangesJButton, gridBagConstraints);

        firstJSeparator.setForeground(resourceMap.getColor("firstJSeparator.foreground")); // NOI18N
        firstJSeparator.setName("firstJSeparator"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 10, 0);
        buttonInnerJPanel.add(firstJSeparator, gridBagConstraints);

        deselectAllChangesJButton.setText(resourceMap.getString("deselectAllChangesJButton.text")); // NOI18N
        deselectAllChangesJButton.setMaximumSize(new java.awt.Dimension(90, 20));
        deselectAllChangesJButton.setMinimumSize(new java.awt.Dimension(90, 20));
        deselectAllChangesJButton.setName("deselectAllChangesJButton"); // NOI18N
        deselectAllChangesJButton.setPreferredSize(new java.awt.Dimension(90, 20));
        deselectAllChangesJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deselectAllChangesJButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 2);
        buttonInnerJPanel.add(deselectAllChangesJButton, gridBagConstraints);

        selectAllChangesJButton.setText(resourceMap.getString("selectAllChangesJButton.text")); // NOI18N
        selectAllChangesJButton.setMaximumSize(new java.awt.Dimension(90, 20));
        selectAllChangesJButton.setMinimumSize(new java.awt.Dimension(90, 20));
        selectAllChangesJButton.setName("selectAllChangesJButton"); // NOI18N
        selectAllChangesJButton.setPreferredSize(new java.awt.Dimension(90, 20));
        selectAllChangesJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllChangesJButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 2);
        buttonInnerJPanel.add(selectAllChangesJButton, gridBagConstraints);

        removeSelectedChangesJButton.setText(resourceMap.getString("removeSelectedChangesJButton.text")); // NOI18N
        removeSelectedChangesJButton.setMaximumSize(new java.awt.Dimension(90, 20));
        removeSelectedChangesJButton.setMinimumSize(new java.awt.Dimension(90, 20));
        removeSelectedChangesJButton.setName("removeSelectedChangesJButton"); // NOI18N
        removeSelectedChangesJButton.setPreferredSize(new java.awt.Dimension(90, 20));
        removeSelectedChangesJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSelectedChangesJButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 0);
        buttonInnerJPanel.add(removeSelectedChangesJButton, gridBagConstraints);

        buttonOuterJPanel.add(buttonInnerJPanel, java.awt.BorderLayout.NORTH);

        eastJPanel.add(buttonOuterJPanel, java.awt.BorderLayout.CENTER);

        eastJScrollPane.setViewportView(eastJPanel);

        mainJPanel.add(eastJScrollPane, java.awt.BorderLayout.EAST);

        getContentPane().add(mainJPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deselectAllChangesJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselectAllChangesJButtonActionPerformed
        defenceAndStorageJList.clearSelection();
        electronicWarefareJList.clearSelection();
        offensiveJList.clearSelection();
        industryJList.clearSelection();
        miscellaneousJList.clearSelection();
    }//GEN-LAST:event_deselectAllChangesJButtonActionPerformed

    private void removeSelectedChangesJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSelectedChangesJButtonActionPerformed
        //Remove from JLists
        defenceAndStorageJListKeyReleased(null);
        electronicWarefareJListKeyReleased(null);
        offensiveJListKeyReleased(null);
        industryJListKeyTyped(null);
        miscellaneousJListKeyReleased(null);

        //Update Stats
        updateStats();
    }//GEN-LAST:event_removeSelectedChangesJButtonActionPerformed

    private void defenceAndStorageJListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_defenceAndStorageJListKeyReleased
        if(evt ==null || "Delete".equals(KeyEvent.getKeyText(evt.getKeyCode())))
        {
            while(defenceAndStorageJList.getSelectedIndex() >= 0)
            {
                defenceAndStorageModel.remove(defenceAndStorageJList.getSelectedIndex());
            }
        }
    }//GEN-LAST:event_defenceAndStorageJListKeyReleased
    private void electronicWarefareJListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_electronicWarefareJListKeyReleased
        if(evt ==null || "Delete".equals(KeyEvent.getKeyText(evt.getKeyCode())))
        {
            while(electronicWarefareJList.getSelectedIndex() >= 0)
            {
                electronicWarefareModel.remove(electronicWarefareJList.getSelectedIndex());
            }
        }
    }//GEN-LAST:event_electronicWarefareJListKeyReleased
    private void offensiveJListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_offensiveJListKeyReleased
        if(evt ==null || "Delete".equals(KeyEvent.getKeyText(evt.getKeyCode())))
        {
            while(offensiveJList.getSelectedIndex() >= 0)
            {
                offensiveModel.remove(offensiveJList.getSelectedIndex());
            }
        }
    }//GEN-LAST:event_offensiveJListKeyReleased
    private void industryJListKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_industryJListKeyTyped
        if(evt ==null || "Delete".equals(KeyEvent.getKeyText(evt.getKeyCode())))
        {
            while(industryJList.getSelectedIndex() >= 0)
            {
                industryModel.remove(industryJList.getSelectedIndex());
            }
        }
    }//GEN-LAST:event_industryJListKeyTyped
    private void miscellaneousJListKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_miscellaneousJListKeyReleased
        if(evt ==null || "Delete".equals(KeyEvent.getKeyText(evt.getKeyCode())))
        {
            while(miscellaneousJList.getSelectedIndex() >= 0)
            {
                miscellaneousModel.remove(miscellaneousJList.getSelectedIndex());
            }
        }
    }//GEN-LAST:event_miscellaneousJListKeyReleased

    private void saveChangesJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveChangesJButtonActionPerformed
        /*
         * moduleDetails[0] = Module Name
         * moduleDetails[1] = Count
         */
        ArrayList<String[]> moduleDetails = new ArrayList<String[]>();
        ArrayList<String> moduleNames = new ArrayList<String>();
        ArrayList<String> uniqModuleNames;

        Object[] modules = defenceAndStorageModel.toArray();
        for(Object module : modules)
        {
            moduleNames.add(((PosModule) module).getName());
        }

        modules = electronicWarefareModel.toArray();
        for(Object module : modules)
        {
            moduleNames.add(((PosModule) module).getName());
        }

        modules = offensiveModel.toArray();
        for(Object module : modules)
        {
            moduleNames.add(((PosModule) module).getName());
        }

        modules = industryModel.toArray();
        for(Object module : modules)
        {
            moduleNames.add(((PosModule) module).getName());
        }

        modules = miscellaneousModel.toArray();
        for(Object module : modules)
        {
            moduleNames.add(((PosModule) module).getName());
        }

        Collections.sort(moduleNames);
        uniqModuleNames = new ArrayList<String>(new HashSet<String>(moduleNames));

        for(String moduleName : uniqModuleNames)
        {
            int count = 0;
            for(String mod : moduleNames){if(moduleName.equals(mod)){count++;}}
            moduleDetails.add(new String[]{moduleName,String.valueOf(count)});
        }

        database.updateDesignSession(designName, controlTower.getName(), moduleDetails);
    }//GEN-LAST:event_saveChangesJButtonActionPerformed

    private void cancelChangesJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelChangesJButtonActionPerformed
        String[] controlTowerName = new String[1];
        ArrayList<String[]> moduleDetails = new ArrayList<String[]>();

        if(!database.loadDesignSession(this.designName, controlTowerName, moduleDetails)){return;}
        if(controlTowerName==null){return;}
        if(moduleDetails!=null || !moduleDetails.isEmpty())
        {
            defenceAndStorageModel.clear();
            electronicWarefareModel.clear();
            offensiveModel.clear();
            industryModel.clear();
            miscellaneousModel.clear();
            for(String[] module : moduleDetails)
            {
                for(int i=0; i<Integer.parseInt(module[1]); i++)
                {
                    this.addModuleToTower(module[0]);
                }
            }
        }
    }//GEN-LAST:event_cancelChangesJButtonActionPerformed

    private void selectAllChangesJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllChangesJButtonActionPerformed
        defenceAndStorageJList.clearSelection();
        electronicWarefareJList.clearSelection();
        offensiveJList.clearSelection();
        industryJList.clearSelection();
        miscellaneousJList.clearSelection();

        int size = defenceAndStorageModel.size();
        int[] indexArray = new int[size];
        for(int i=0; i<size; i++)
        {
            indexArray[i]=i;
        }
        defenceAndStorageJList.setSelectedIndices(indexArray);

        size = electronicWarefareModel.size();
        indexArray = new int[size];
        for(int i=0; i<size; i++)
        {
            indexArray[i]=i;
        }
        electronicWarefareJList.setSelectedIndices(indexArray);

        size = offensiveModel.size();
        indexArray = new int[size];
        for(int i=0; i<size; i++)
        {
            indexArray[i]=i;
        }
        offensiveJList.setSelectedIndices(indexArray);

        size = industryModel.size();
        indexArray = new int[size];
        for(int i=0; i<size; i++)
        {
            indexArray[i]=i;
        }
        industryJList.setSelectedIndices(indexArray);

        size = miscellaneousModel.size();
        indexArray = new int[size];
        for(int i=0; i<size; i++)
        {
            indexArray[i]=i;
        }
        miscellaneousJList.setSelectedIndices(indexArray);
    }//GEN-LAST:event_selectAllChangesJButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel armorHPJLabel;
    private javax.swing.JTextField armorHPJTextField;
    private javax.swing.JPanel buttonInnerJPanel;
    private javax.swing.JPanel buttonOuterJPanel;
    private javax.swing.JButton cancelChangesJButton;
    private javax.swing.JPanel centerJPanel;
    private javax.swing.JLabel countOfModulesJLabel;
    private javax.swing.JTextField countOfModulesJTextField;
    private javax.swing.JLabel cpuJLabel;
    private javax.swing.JProgressBar cpuJProgressBar;
    private javax.swing.JList defenceAndStorageJList;
    private javax.swing.JPanel defenceAndStorageJPanel;
    private javax.swing.JScrollPane defenceAndStorageJScrollPane;
    private javax.swing.JButton deselectAllChangesJButton;
    private javax.swing.JLabel designNameJLabel;
    private javax.swing.JTextField designNameJTextField;
    private javax.swing.JPanel eastJPanel;
    private javax.swing.JScrollPane eastJScrollPane;
    private javax.swing.JList electronicWarefareJList;
    private javax.swing.JPanel electronicWarefareJPanel;
    private javax.swing.JScrollPane electronicWarefareJScrollPane;
    private javax.swing.JSeparator fifthJSeparator;
    private javax.swing.JSeparator firstJSeparator;
    private javax.swing.JSeparator fourthJSeparator;
    private javax.swing.JList industryJList;
    private javax.swing.JPanel industryJPanel;
    private javax.swing.JScrollPane industryJScrollPane;
    private javax.swing.JPanel mainJPanel;
    private javax.swing.JList miscellaneousJList;
    private javax.swing.JPanel miscellaneousJPanel;
    private javax.swing.JScrollPane miscellaneousJScrollPane;
    private javax.swing.JList offensiveJList;
    private javax.swing.JPanel offensiveJPanel;
    private javax.swing.JScrollPane offensiveJScrollPane;
    private javax.swing.JPanel posModulesJPanel;
    private javax.swing.JScrollPane posModulesJScrollPane;
    private javax.swing.JPanel posStatisticsJPanel;
    private javax.swing.JLabel powergirdJLabel;
    private javax.swing.JProgressBar powergridJProgressBar;
    private javax.swing.JButton removeSelectedChangesJButton;
    private javax.swing.JButton saveChangesJButton;
    private javax.swing.JSeparator secondJSeparator;
    private javax.swing.JButton selectAllChangesJButton;
    private javax.swing.JLabel shieldHPJLabel;
    private javax.swing.JTextField shieldHPJTextField;
    private javax.swing.JLabel structureHPJLabel;
    private javax.swing.JTextField structureHPJTextField;
    private javax.swing.JSeparator thirdJSeparator;
    private javax.swing.JLabel totalMassJLabel;
    private javax.swing.JTextField totalMassJTextField;
    private javax.swing.JLabel totalVolumneJLabel;
    private javax.swing.JTextField totalVolumneJTextField;
    private javax.swing.JLabel towerNameJLabel;
    private javax.swing.JTextField towerNameJTextField;
    private javax.swing.JLabel towerSizeJLabel;
    private javax.swing.JTextField towerSizeJTextField;
    // End of variables declaration//GEN-END:variables

    private DecimalFormat formatter = new DecimalFormat("###,###,###,###,###");
    protected String designName;
    private EvePosMad_Database database;
    private PosControlTower controlTower;
    private DefaultListModel defenceAndStorageModel, electronicWarefareModel,
                             offensiveModel, industryModel, miscellaneousModel;
}
