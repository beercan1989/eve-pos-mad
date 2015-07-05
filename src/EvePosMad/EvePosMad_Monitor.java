package eveposmad;
import com.beimin.eveapi.corporation.starbase.list.*;
import com.beimin.eveapi.corporation.starbase.detail.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.swing.JOptionPane;
import org.jdesktop.application.FrameView;
/**
 * @author James Bacon
 */
public class EvePosMad_Monitor extends javax.swing.JInternalFrame {

    /** Creates new form EvePosMad_Monitor */
    public EvePosMad_Monitor(StarbaseDetailResponse posDetails, 
    StarbaseListResponse posList, long posItemID, EvePosMad_View parentWindow,
    String apiKey, String userID, String characterID, String structureID, 
    String monitorName, boolean saveButtonState) {
        this.saveButtonState = saveButtonState;
        initComponents();
        database = new EvePosMad_Database();
        this.posDetails = posDetails;
        this.posList = posList;
        this.parentWindow = parentWindow;
        this.apiKey = apiKey;
        this.userID = userID;
        this.characterID = characterID;
        this.structureID = structureID;
        this.monitorName = monitorName;
        this.posTypeID = 0L;
        setupMainPosStats(posItemID);
        updateFuelStats();
    }

    public String getID()
    {
        return monitorName;
    }
    private void updateFuelStats(){
        Map<Integer, Integer> fuelMap = posDetails.getFuelMap();
        for(Integer fuelKey : fuelMap.keySet())
        {
            String fuelAmount = fuelMap.get(fuelKey).toString();
            switch(fuelKey)
            {
                case 44:    enrichedUraniumQuantityJTextField.setText(fuelAmount); break; //Enriched Uranium
                case 3683:  oxygenQuantityJTextFiel.setText(fuelAmount); break; //Oxygen
                case 3689:  mechanicalPartsQuantityJTextField.setText(fuelAmount); break; //Mechanical Parts
                case 9832:  coolantQuantityJTextField.setText(fuelAmount); break; //Coolant
                case 9848:  roboticsQuantityJTextField.setText(fuelAmount); break; //Robotics
                case 16272: heavyWaterQuantityJTextField.setText(fuelAmount); break; //Heavy Water
                case 16273: liquidOzoneQuantityJTextField.setText(fuelAmount); break; //Liquid Ozone
                case 16275: strontiumQuantityJTextField.setText(fuelAmount); break; //Strontium Clathrates
                case 24592: //Amarr Empire Starbase Charter
                case 24593: //Caldari State Starbase Charter
                case 24594: //Gallente Federation Starbase Charter
                case 24595: //Minmatar Republic Starbase Charter
                case 24596: //Khanid Kingdom Starbase Charter
                case 24597: //Ammatar Mandate Starbase Charter
                            charterQuantityJTextField.setText(fuelAmount); break;
                default:    isotopesQuantityJTextField.setText(fuelAmount); break; //The Isotopes
            }
        }

        long enriched=1L, oxygen=1L, mech=1L, coolant=1L, robotics=1L, stront=1L, isotopes=1L, charter=1L;
        for(long[] fuel : database.getTowerFuelLevels(posTypeID))
        {
            if(fuel[0]==44L)   {enriched = fuel[1];}
            if(fuel[0]==3683L) {oxygen   = fuel[1];}
            if(fuel[0]==3689L) {mech     = fuel[1];}
            if(fuel[0]==9832L) {coolant  = fuel[1];}
            if(fuel[0]==9848L) {robotics = fuel[1];}
            if(fuel[0]==16275L){stront   = fuel[1];}
            if(fuel[0]==16274L){isotopes = fuel[1];}
            if(fuel[0]==17887L){isotopes = fuel[1];}
            if(fuel[0]==17888L){isotopes = fuel[1];}
            if(fuel[0]==17889L){isotopes = fuel[1];}
        }


        if(charterQuantityJTextField.getText().isEmpty() || "0".equals(charterQuantityJTextField.getText()) )
        {
            charterQuantityJTextField.setText("0"); charterTimeJTextField.setText("N/A");
        }
        else
        { charterTimeJTextField.setText(calcTimeLeft(Long.parseLong(charterQuantityJTextField.getText()), charter)); }

        if(enrichedUraniumQuantityJTextField.getText().isEmpty() || "0".equals(enrichedUraniumQuantityJTextField.getText()) )
        {
            enrichedUraniumQuantityJTextField.setText("0"); enrichedUraniumTimeJTextField.setText("N/A");
        }
        else
        { enrichedUraniumTimeJTextField.setText(calcTimeLeft(Long.parseLong(enrichedUraniumQuantityJTextField.getText()), enriched)); }

        if(oxygenQuantityJTextFiel.getText().isEmpty() || "0".equals(oxygenQuantityJTextFiel.getText()) )
        {
            oxygenQuantityJTextFiel.setText("0"); oxygenTimeJTextField.setText("N/A");
        }
        else
        { oxygenTimeJTextField.setText(calcTimeLeft(Long.parseLong(oxygenQuantityJTextFiel.getText()), oxygen)); }

        if(mechanicalPartsQuantityJTextField.getText().isEmpty() || "0".equals(mechanicalPartsQuantityJTextField.getText()) )
        { 
            mechanicalPartsQuantityJTextField.setText("0"); mechanicalPartsTimeJTextField.setText("N/A");
        }
        else
        { mechanicalPartsTimeJTextField.setText(calcTimeLeft(Long.parseLong(mechanicalPartsQuantityJTextField.getText()), mech)); }

        if(coolantQuantityJTextField.getText().isEmpty() || "0".equals(coolantQuantityJTextField.getText()) )
        {
            coolantQuantityJTextField.setText("0"); coolantTimeJTextField.setText("N/A");
        }
        else
        { coolantTimeJTextField.setText(calcTimeLeft(Long.parseLong(coolantQuantityJTextField.getText()), coolant)); }

        if(roboticsQuantityJTextField.getText().isEmpty() || "0".equals(roboticsQuantityJTextField.getText()) )
        {
            roboticsQuantityJTextField.setText("0"); roboticsTimeJTextField.setText("N/A");
        }
        else
        { roboticsTimeJTextField.setText(calcTimeLeft(Long.parseLong(roboticsQuantityJTextField.getText()), robotics)); }

        if(heavyWaterQuantityJTextField.getText().isEmpty() || "0".equals(heavyWaterQuantityJTextField.getText()) )
        {
            heavyWaterQuantityJTextField.setText("0"); heavyWaterTimeJTextField.setText("N/A");
        }

        if(liquidOzoneQuantityJTextField.getText().isEmpty() || "0".equals(liquidOzoneQuantityJTextField.getText()) )
        {
            liquidOzoneQuantityJTextField.setText("0"); liquidOzoneTimeJTextField.setText("N/A");
        }

        if(strontiumQuantityJTextField.getText().isEmpty() || "0".equals(strontiumQuantityJTextField.getText()) )
        {
            strontiumQuantityJTextField.setText("0"); strontiumTimeJTextField.setText("N/A");
        }
        else
        { strontiumTimeJTextField.setText(calcTimeLeft(Long.parseLong(strontiumQuantityJTextField.getText()), stront)); }

        if(isotopesQuantityJTextField.getText().isEmpty() || "0".equals(isotopesQuantityJTextField.getText()) )
        { 
            isotopesQuantityJTextField.setText("0"); isotopesTimeJTextField.setText("N/A");
        }
        else
        { isotopesTimeJTextField.setText(calcTimeLeft(Long.parseLong(isotopesQuantityJTextField.getText()), isotopes)); }
    }

    private void setupMainPosStats(long posItemID){
        ApiStarbase posToMonitor = new ApiStarbase();
        for(ApiStarbase pos : posList.getStarbases())
        {
            if(pos.getItemID()==posItemID)
            {
                posToMonitor = pos;
            }
        }
        long solarSytemID = posToMonitor.getLocationID();
        long moonID = posToMonitor.getMoonID();
        long typeID = posToMonitor.getTypeID();
        posTypeID = typeID;
        String[] locationDetails = database.getLocationDetails(moonID, solarSytemID);

        //Double securtiyLevel = Double.valueOf(new DecimalFormat("#.##").format(locationDetails[1]));

        this.systemMoonJTextField.setText(locationDetails[0]);
        this.systemSecurityLevelJTextField.setText(locationDetails[1]);
        //this.systemSecurityLevelJTextField.setText(securtiyLevel.toString());
        this.constellationJTextField.setText(locationDetails[2]);
        this.regionJTextField.setText(locationDetails[3]);
        this.universeJTextField.setText(locationDetails[4]);
        this.typeJTextField.setText(database.getTypeName(typeID));
        this.isotopesJLabel.setText(database.getTowerIsotope(typeID));

        String state = "";
        switch(posToMonitor.getState())
        {
            case 0: state = "Unanchored"; break;
            case 1: state = "Anchored But Offline"; break;
            case 2: state = "Onlining"; break;
            case 3: state = "Reinforced"; break;
            case 4: state = "Online"; break;
        }
        this.currentStateJTextField.setText(state);

        this.stateDTJTextField.setText(posToMonitor.getStateTimestamp().toString());
        this.onlinedDTJTextField.setText(posToMonitor.getOnlineTimestamp().toString());
        this.lastUpDatedAtJTextField.setText(posDetails.getCurrentTime().toString());
        this.nextUpDatedAtJTextField.setText(posDetails.getCachedUntil().toString());

        this.allianceUseJTextField.setText(Boolean.toString(posDetails.isAllowAllianceMembers()));
        this.corpUseJTextField.setText(Boolean.toString(posDetails.isAllowCorporationMembers()));
        this.aOAJTextField.setText(Boolean.toString(posDetails.getOnAggression().isEnabled()));
        this.aOWTJTextField.setText(Boolean.toString(posDetails.getOnCorporationWar().isEnabled()));
        this.aOSSJTextField.setText(Boolean.toString(posDetails.getOnStatusDrop().isEnabled()));
        this.aOSJTextField.setText(Boolean.toString(posDetails.getOnStandingDrop().isEnabled()));
    }

    private String calcTimeLeft(long currentQuanity, long quanityPerHour)
    {
        long estimatedHours = currentQuanity / quanityPerHour;
        long days = estimatedHours / 24;
        long hours = estimatedHours % 24;
        return days+"d "+hours+"h";
    }

    /** This method is called from within the constructor to
     * initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        mainJPanel = new javax.swing.JPanel();
        currentFuelLevelsOuterJPanel = new javax.swing.JPanel();
        currentFuelLevelsInnerJPanel = new javax.swing.JPanel();
        fuelNameJLabel = new javax.swing.JLabel();
        fuelQuantityJLabel = new javax.swing.JLabel();
        fuelTimeJLabel = new javax.swing.JLabel();
        enrichedUraniumJLabel = new javax.swing.JLabel();
        enrichedUraniumQuantityJTextField = new javax.swing.JTextField();
        oxygenJLabel = new javax.swing.JLabel();
        oxygenQuantityJTextFiel = new javax.swing.JTextField();
        mechanicalPartsJLabel = new javax.swing.JLabel();
        mechanicalPartsQuantityJTextField = new javax.swing.JTextField();
        coolantJLabel = new javax.swing.JLabel();
        coolantQuantityJTextField = new javax.swing.JTextField();
        roboticsJLabel = new javax.swing.JLabel();
        roboticsQuantityJTextField = new javax.swing.JTextField();
        isotopesJLabel = new javax.swing.JLabel();
        isotopesQuantityJTextField = new javax.swing.JTextField();
        charterJLabel = new javax.swing.JLabel();
        charterQuantityJTextField = new javax.swing.JTextField();
        liquidOzoneJLabel = new javax.swing.JLabel();
        liquidOzoneQuantityJTextField = new javax.swing.JTextField();
        heavyWaterJLabel = new javax.swing.JLabel();
        heavyWaterQuantityJTextField = new javax.swing.JTextField();
        strontiumJLabel = new javax.swing.JLabel();
        strontiumQuantityJTextField = new javax.swing.JTextField();
        enrichedUraniumTimeJTextField = new javax.swing.JTextField();
        strontiumTimeJTextField = new javax.swing.JTextField();
        heavyWaterTimeJTextField = new javax.swing.JTextField();
        liquidOzoneTimeJTextField = new javax.swing.JTextField();
        charterTimeJTextField = new javax.swing.JTextField();
        isotopesTimeJTextField = new javax.swing.JTextField();
        roboticsTimeJTextField = new javax.swing.JTextField();
        coolantTimeJTextField = new javax.swing.JTextField();
        mechanicalPartsTimeJTextField = new javax.swing.JTextField();
        oxygenTimeJTextField = new javax.swing.JTextField();
        firstJSeparator = new javax.swing.JSeparator();
        secondJSeparator = new javax.swing.JSeparator();
        thirdJSeparator = new javax.swing.JSeparator();
        mainPosDetailsOuterJPanel = new javax.swing.JPanel();
        mainPosDetailsInnerJPanel = new javax.swing.JPanel();
        stateDTJLabel = new javax.swing.JLabel();
        stateDTJTextField = new javax.swing.JTextField();
        constellationJTextField = new javax.swing.JTextField();
        constellationJLabel = new javax.swing.JLabel();
        systemMoonJTextField = new javax.swing.JTextField();
        systemMoonJLabel = new javax.swing.JLabel();
        universeJTextField = new javax.swing.JTextField();
        universeJLabel = new javax.swing.JLabel();
        typeJTextField = new javax.swing.JTextField();
        typeJLabel = new javax.swing.JLabel();
        systemSecurityLevelJTextField = new javax.swing.JTextField();
        systemSecurityLevelJLabel = new javax.swing.JLabel();
        onlinedDTJTextField = new javax.swing.JTextField();
        onlinedDTJLabel = new javax.swing.JLabel();
        aOSJTextField = new javax.swing.JTextField();
        aOSJLabel = new javax.swing.JLabel();
        regionJTextField = new javax.swing.JTextField();
        regionJLabel = new javax.swing.JLabel();
        nextUpDatedAtJLabel = new javax.swing.JLabel();
        nextUpDatedAtJTextField = new javax.swing.JTextField();
        lastUpDatedAtJLabel = new javax.swing.JLabel();
        lastUpDatedAtJTextField = new javax.swing.JTextField();
        currentStateJLabel = new javax.swing.JLabel();
        currentStateJTextField = new javax.swing.JTextField();
        corpUseJLabel = new javax.swing.JLabel();
        corpUseJTextField = new javax.swing.JTextField();
        allianceUseJLabel = new javax.swing.JLabel();
        allianceUseJTextField = new javax.swing.JTextField();
        aOSSJTextField = new javax.swing.JTextField();
        aOSSJLabel = new javax.swing.JLabel();
        aOWTJTextField = new javax.swing.JTextField();
        aOWTJLabel = new javax.swing.JLabel();
        aOAJTextField = new javax.swing.JTextField();
        aOAJLabel = new javax.swing.JLabel();
        buttonsOuterJPanel = new javax.swing.JPanel();
        buttonsInnerJPanel = new javax.swing.JPanel();
        addMonitorToList = new javax.swing.JButton();
        refreshPosDetails = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(EvePosMad_Monitor.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setVisible(true);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        mainJPanel.setName("mainJPanel"); // NOI18N
        mainJPanel.setLayout(new java.awt.GridBagLayout());

        currentFuelLevelsOuterJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        currentFuelLevelsOuterJPanel.setName("currentFuelLevelsOuterJPanel"); // NOI18N
        currentFuelLevelsOuterJPanel.setLayout(new java.awt.BorderLayout());

        currentFuelLevelsInnerJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("currentFuelLevelsInnerJPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("currentFuelLevelsInnerJPanel.border.titleFont"), resourceMap.getColor("currentFuelLevelsInnerJPanel.border.titleColor"))); // NOI18N
        currentFuelLevelsInnerJPanel.setName("currentFuelLevelsInnerJPanel"); // NOI18N
        currentFuelLevelsInnerJPanel.setLayout(new java.awt.GridBagLayout());

        fuelNameJLabel.setFont(resourceMap.getFont("fuelNameJLabel.font")); // NOI18N
        fuelNameJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        fuelNameJLabel.setText(resourceMap.getString("fuelNameJLabel.text")); // NOI18N
        fuelNameJLabel.setName("fuelNameJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(fuelNameJLabel, gridBagConstraints);

        fuelQuantityJLabel.setFont(resourceMap.getFont("fuelQuantityJLabel.font")); // NOI18N
        fuelQuantityJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        fuelQuantityJLabel.setText(resourceMap.getString("fuelQuantityJLabel.text")); // NOI18N
        fuelQuantityJLabel.setName("fuelQuantityJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        currentFuelLevelsInnerJPanel.add(fuelQuantityJLabel, gridBagConstraints);

        fuelTimeJLabel.setFont(resourceMap.getFont("fuelTimeJLabel.font")); // NOI18N
        fuelTimeJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        fuelTimeJLabel.setText(resourceMap.getString("fuelTimeJLabel.text")); // NOI18N
        fuelTimeJLabel.setName("fuelTimeJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        currentFuelLevelsInnerJPanel.add(fuelTimeJLabel, gridBagConstraints);

        enrichedUraniumJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        enrichedUraniumJLabel.setText(resourceMap.getString("enrichedUraniumJLabel.text")); // NOI18N
        enrichedUraniumJLabel.setName("enrichedUraniumJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(enrichedUraniumJLabel, gridBagConstraints);

        enrichedUraniumQuantityJTextField.setEditable(false);
        enrichedUraniumQuantityJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        enrichedUraniumQuantityJTextField.setText(resourceMap.getString("enrichedUraniumQuantityJTextField.text")); // NOI18N
        enrichedUraniumQuantityJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        enrichedUraniumQuantityJTextField.setName("enrichedUraniumQuantityJTextField"); // NOI18N
        enrichedUraniumQuantityJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(enrichedUraniumQuantityJTextField, gridBagConstraints);

        oxygenJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        oxygenJLabel.setText(resourceMap.getString("oxygenJLabel.text")); // NOI18N
        oxygenJLabel.setName("oxygenJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(oxygenJLabel, gridBagConstraints);

        oxygenQuantityJTextFiel.setEditable(false);
        oxygenQuantityJTextFiel.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        oxygenQuantityJTextFiel.setText(resourceMap.getString("oxygenQuantityJTextFiel.text")); // NOI18N
        oxygenQuantityJTextFiel.setMinimumSize(new java.awt.Dimension(75, 20));
        oxygenQuantityJTextFiel.setName("oxygenQuantityJTextFiel"); // NOI18N
        oxygenQuantityJTextFiel.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(oxygenQuantityJTextFiel, gridBagConstraints);

        mechanicalPartsJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mechanicalPartsJLabel.setText(resourceMap.getString("mechanicalPartsJLabel.text")); // NOI18N
        mechanicalPartsJLabel.setName("mechanicalPartsJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(mechanicalPartsJLabel, gridBagConstraints);

        mechanicalPartsQuantityJTextField.setEditable(false);
        mechanicalPartsQuantityJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mechanicalPartsQuantityJTextField.setText(resourceMap.getString("mechanicalPartsQuantityJTextField.text")); // NOI18N
        mechanicalPartsQuantityJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        mechanicalPartsQuantityJTextField.setName("mechanicalPartsQuantityJTextField"); // NOI18N
        mechanicalPartsQuantityJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(mechanicalPartsQuantityJTextField, gridBagConstraints);

        coolantJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        coolantJLabel.setText(resourceMap.getString("coolantJLabel.text")); // NOI18N
        coolantJLabel.setName("coolantJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(coolantJLabel, gridBagConstraints);

        coolantQuantityJTextField.setEditable(false);
        coolantQuantityJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        coolantQuantityJTextField.setText(resourceMap.getString("coolantQuantityJTextField.text")); // NOI18N
        coolantQuantityJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        coolantQuantityJTextField.setName("coolantQuantityJTextField"); // NOI18N
        coolantQuantityJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(coolantQuantityJTextField, gridBagConstraints);

        roboticsJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        roboticsJLabel.setText(resourceMap.getString("roboticsJLabel.text")); // NOI18N
        roboticsJLabel.setName("roboticsJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(roboticsJLabel, gridBagConstraints);

        roboticsQuantityJTextField.setEditable(false);
        roboticsQuantityJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        roboticsQuantityJTextField.setText(resourceMap.getString("roboticsQuantityJTextField.text")); // NOI18N
        roboticsQuantityJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        roboticsQuantityJTextField.setName("roboticsQuantityJTextField"); // NOI18N
        roboticsQuantityJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(roboticsQuantityJTextField, gridBagConstraints);

        isotopesJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        isotopesJLabel.setText(resourceMap.getString("isotopesJLabel.text")); // NOI18N
        isotopesJLabel.setName("isotopesJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(isotopesJLabel, gridBagConstraints);

        isotopesQuantityJTextField.setEditable(false);
        isotopesQuantityJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        isotopesQuantityJTextField.setText(resourceMap.getString("isotopesQuantityJTextField.text")); // NOI18N
        isotopesQuantityJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        isotopesQuantityJTextField.setName("isotopesQuantityJTextField"); // NOI18N
        isotopesQuantityJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(isotopesQuantityJTextField, gridBagConstraints);

        charterJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        charterJLabel.setText(resourceMap.getString("charterJLabel.text")); // NOI18N
        charterJLabel.setName("charterJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(charterJLabel, gridBagConstraints);

        charterQuantityJTextField.setEditable(false);
        charterQuantityJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        charterQuantityJTextField.setText(resourceMap.getString("charterQuantityJTextField.text")); // NOI18N
        charterQuantityJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        charterQuantityJTextField.setName("charterQuantityJTextField"); // NOI18N
        charterQuantityJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(charterQuantityJTextField, gridBagConstraints);

        liquidOzoneJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        liquidOzoneJLabel.setText(resourceMap.getString("liquidOzoneJLabel.text")); // NOI18N
        liquidOzoneJLabel.setName("liquidOzoneJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(liquidOzoneJLabel, gridBagConstraints);

        liquidOzoneQuantityJTextField.setEditable(false);
        liquidOzoneQuantityJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        liquidOzoneQuantityJTextField.setText(resourceMap.getString("liquidOzoneQuantityJTextField.text")); // NOI18N
        liquidOzoneQuantityJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        liquidOzoneQuantityJTextField.setName("liquidOzoneQuantityJTextField"); // NOI18N
        liquidOzoneQuantityJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(liquidOzoneQuantityJTextField, gridBagConstraints);

        heavyWaterJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        heavyWaterJLabel.setText(resourceMap.getString("heavyWaterJLabel.text")); // NOI18N
        heavyWaterJLabel.setName("heavyWaterJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(heavyWaterJLabel, gridBagConstraints);

        heavyWaterQuantityJTextField.setEditable(false);
        heavyWaterQuantityJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        heavyWaterQuantityJTextField.setText(resourceMap.getString("heavyWaterQuantityJTextField.text")); // NOI18N
        heavyWaterQuantityJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        heavyWaterQuantityJTextField.setName("heavyWaterQuantityJTextField"); // NOI18N
        heavyWaterQuantityJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(heavyWaterQuantityJTextField, gridBagConstraints);

        strontiumJLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        strontiumJLabel.setText(resourceMap.getString("strontiumJLabel.text")); // NOI18N
        strontiumJLabel.setName("strontiumJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(strontiumJLabel, gridBagConstraints);

        strontiumQuantityJTextField.setEditable(false);
        strontiumQuantityJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        strontiumQuantityJTextField.setText(resourceMap.getString("strontiumQuantityJTextField.text")); // NOI18N
        strontiumQuantityJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        strontiumQuantityJTextField.setName("strontiumQuantityJTextField"); // NOI18N
        strontiumQuantityJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        currentFuelLevelsInnerJPanel.add(strontiumQuantityJTextField, gridBagConstraints);

        enrichedUraniumTimeJTextField.setEditable(false);
        enrichedUraniumTimeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        enrichedUraniumTimeJTextField.setText(resourceMap.getString("enrichedUraniumTimeJTextField.text")); // NOI18N
        enrichedUraniumTimeJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        enrichedUraniumTimeJTextField.setName("enrichedUraniumTimeJTextField"); // NOI18N
        enrichedUraniumTimeJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        currentFuelLevelsInnerJPanel.add(enrichedUraniumTimeJTextField, gridBagConstraints);

        strontiumTimeJTextField.setEditable(false);
        strontiumTimeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        strontiumTimeJTextField.setText(resourceMap.getString("strontiumTimeJTextField.text")); // NOI18N
        strontiumTimeJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        strontiumTimeJTextField.setName("strontiumTimeJTextField"); // NOI18N
        strontiumTimeJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        currentFuelLevelsInnerJPanel.add(strontiumTimeJTextField, gridBagConstraints);

        heavyWaterTimeJTextField.setEditable(false);
        heavyWaterTimeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        heavyWaterTimeJTextField.setText(resourceMap.getString("heavyWaterTimeJTextField.text")); // NOI18N
        heavyWaterTimeJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        heavyWaterTimeJTextField.setName("heavyWaterTimeJTextField"); // NOI18N
        heavyWaterTimeJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        currentFuelLevelsInnerJPanel.add(heavyWaterTimeJTextField, gridBagConstraints);

        liquidOzoneTimeJTextField.setEditable(false);
        liquidOzoneTimeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        liquidOzoneTimeJTextField.setText(resourceMap.getString("liquidOzoneTimeJTextField.text")); // NOI18N
        liquidOzoneTimeJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        liquidOzoneTimeJTextField.setName("liquidOzoneTimeJTextField"); // NOI18N
        liquidOzoneTimeJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        currentFuelLevelsInnerJPanel.add(liquidOzoneTimeJTextField, gridBagConstraints);

        charterTimeJTextField.setEditable(false);
        charterTimeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        charterTimeJTextField.setText(resourceMap.getString("charterTimeJTextField.text")); // NOI18N
        charterTimeJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        charterTimeJTextField.setName("charterTimeJTextField"); // NOI18N
        charterTimeJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        currentFuelLevelsInnerJPanel.add(charterTimeJTextField, gridBagConstraints);

        isotopesTimeJTextField.setEditable(false);
        isotopesTimeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        isotopesTimeJTextField.setText(resourceMap.getString("isotopesTimeJTextField.text")); // NOI18N
        isotopesTimeJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        isotopesTimeJTextField.setName("isotopesTimeJTextField"); // NOI18N
        isotopesTimeJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        currentFuelLevelsInnerJPanel.add(isotopesTimeJTextField, gridBagConstraints);

        roboticsTimeJTextField.setEditable(false);
        roboticsTimeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        roboticsTimeJTextField.setText(resourceMap.getString("roboticsTimeJTextField.text")); // NOI18N
        roboticsTimeJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        roboticsTimeJTextField.setName("roboticsTimeJTextField"); // NOI18N
        roboticsTimeJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        currentFuelLevelsInnerJPanel.add(roboticsTimeJTextField, gridBagConstraints);

        coolantTimeJTextField.setEditable(false);
        coolantTimeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        coolantTimeJTextField.setText(resourceMap.getString("coolantTimeJTextField.text")); // NOI18N
        coolantTimeJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        coolantTimeJTextField.setName("coolantTimeJTextField"); // NOI18N
        coolantTimeJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        currentFuelLevelsInnerJPanel.add(coolantTimeJTextField, gridBagConstraints);

        mechanicalPartsTimeJTextField.setEditable(false);
        mechanicalPartsTimeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mechanicalPartsTimeJTextField.setText(resourceMap.getString("mechanicalPartsTimeJTextField.text")); // NOI18N
        mechanicalPartsTimeJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        mechanicalPartsTimeJTextField.setName("mechanicalPartsTimeJTextField"); // NOI18N
        mechanicalPartsTimeJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        currentFuelLevelsInnerJPanel.add(mechanicalPartsTimeJTextField, gridBagConstraints);

        oxygenTimeJTextField.setEditable(false);
        oxygenTimeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        oxygenTimeJTextField.setText(resourceMap.getString("oxygenTimeJTextField.text")); // NOI18N
        oxygenTimeJTextField.setMinimumSize(new java.awt.Dimension(75, 20));
        oxygenTimeJTextField.setName("oxygenTimeJTextField"); // NOI18N
        oxygenTimeJTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        currentFuelLevelsInnerJPanel.add(oxygenTimeJTextField, gridBagConstraints);

        firstJSeparator.setForeground(resourceMap.getColor("firstJSeparator.foreground")); // NOI18N
        firstJSeparator.setName("firstJSeparator"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 10);
        currentFuelLevelsInnerJPanel.add(firstJSeparator, gridBagConstraints);

        secondJSeparator.setBackground(resourceMap.getColor("secondJSeparator.background")); // NOI18N
        secondJSeparator.setForeground(resourceMap.getColor("secondJSeparator.foreground")); // NOI18N
        secondJSeparator.setName("secondJSeparator"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 10);
        currentFuelLevelsInnerJPanel.add(secondJSeparator, gridBagConstraints);

        thirdJSeparator.setForeground(resourceMap.getColor("thirdJSeparator.foreground")); // NOI18N
        thirdJSeparator.setName("thirdJSeparator"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 10);
        currentFuelLevelsInnerJPanel.add(thirdJSeparator, gridBagConstraints);

        currentFuelLevelsOuterJPanel.add(currentFuelLevelsInnerJPanel, java.awt.BorderLayout.NORTH);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        mainJPanel.add(currentFuelLevelsOuterJPanel, gridBagConstraints);

        mainPosDetailsOuterJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainPosDetailsOuterJPanel.setName("mainPosDetailsOuterJPanel"); // NOI18N
        mainPosDetailsOuterJPanel.setLayout(new java.awt.BorderLayout());

        mainPosDetailsInnerJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("mainPosDetailsInnerJPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("mainPosDetailsInnerJPanel.border.titleFont"), resourceMap.getColor("mainPosDetailsInnerJPanel.border.titleColor"))); // NOI18N
        mainPosDetailsInnerJPanel.setMaximumSize(null);
        mainPosDetailsInnerJPanel.setName("mainPosDetailsInnerJPanel"); // NOI18N
        mainPosDetailsInnerJPanel.setPreferredSize(null);
        mainPosDetailsInnerJPanel.setLayout(new java.awt.GridBagLayout());

        stateDTJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        stateDTJLabel.setText(resourceMap.getString("stateDTJLabel.text")); // NOI18N
        stateDTJLabel.setName("stateDTJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(stateDTJLabel, gridBagConstraints);

        stateDTJTextField.setEditable(false);
        stateDTJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        stateDTJTextField.setText(resourceMap.getString("stateDTJTextField.text")); // NOI18N
        stateDTJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        stateDTJTextField.setName("stateDTJTextField"); // NOI18N
        stateDTJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(stateDTJTextField, gridBagConstraints);

        constellationJTextField.setEditable(false);
        constellationJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        constellationJTextField.setText(resourceMap.getString("constellationJTextField.text")); // NOI18N
        constellationJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        constellationJTextField.setName("constellationJTextField"); // NOI18N
        constellationJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(constellationJTextField, gridBagConstraints);

        constellationJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        constellationJLabel.setText(resourceMap.getString("constellationJLabel.text")); // NOI18N
        constellationJLabel.setName("constellationJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(constellationJLabel, gridBagConstraints);

        systemMoonJTextField.setEditable(false);
        systemMoonJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        systemMoonJTextField.setText(resourceMap.getString("systemMoonJTextField.text")); // NOI18N
        systemMoonJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        systemMoonJTextField.setName("systemMoonJTextField"); // NOI18N
        systemMoonJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(systemMoonJTextField, gridBagConstraints);

        systemMoonJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        systemMoonJLabel.setText(resourceMap.getString("systemMoonJLabel.text")); // NOI18N
        systemMoonJLabel.setName("systemMoonJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(systemMoonJLabel, gridBagConstraints);

        universeJTextField.setEditable(false);
        universeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        universeJTextField.setText(resourceMap.getString("universeJTextField.text")); // NOI18N
        universeJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        universeJTextField.setName("universeJTextField"); // NOI18N
        universeJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(universeJTextField, gridBagConstraints);

        universeJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        universeJLabel.setText(resourceMap.getString("universeJLabel.text")); // NOI18N
        universeJLabel.setName("universeJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(universeJLabel, gridBagConstraints);

        typeJTextField.setEditable(false);
        typeJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        typeJTextField.setText(resourceMap.getString("typeJTextField.text")); // NOI18N
        typeJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        typeJTextField.setName("typeJTextField"); // NOI18N
        typeJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(typeJTextField, gridBagConstraints);

        typeJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        typeJLabel.setText(resourceMap.getString("typeJLabel.text")); // NOI18N
        typeJLabel.setName("typeJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(typeJLabel, gridBagConstraints);

        systemSecurityLevelJTextField.setEditable(false);
        systemSecurityLevelJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        systemSecurityLevelJTextField.setText(resourceMap.getString("systemSecurityLevelJTextField.text")); // NOI18N
        systemSecurityLevelJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        systemSecurityLevelJTextField.setName("systemSecurityLevelJTextField"); // NOI18N
        systemSecurityLevelJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(systemSecurityLevelJTextField, gridBagConstraints);

        systemSecurityLevelJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        systemSecurityLevelJLabel.setText(resourceMap.getString("systemSecurityLevelJLabel.text")); // NOI18N
        systemSecurityLevelJLabel.setName("systemSecurityLevelJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(systemSecurityLevelJLabel, gridBagConstraints);

        onlinedDTJTextField.setEditable(false);
        onlinedDTJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        onlinedDTJTextField.setText(resourceMap.getString("onlinedDTJTextField.text")); // NOI18N
        onlinedDTJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        onlinedDTJTextField.setName("onlinedDTJTextField"); // NOI18N
        onlinedDTJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(onlinedDTJTextField, gridBagConstraints);

        onlinedDTJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        onlinedDTJLabel.setText(resourceMap.getString("onlinedDTJLabel.text")); // NOI18N
        onlinedDTJLabel.setName("onlinedDTJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(onlinedDTJLabel, gridBagConstraints);

        aOSJTextField.setEditable(false);
        aOSJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aOSJTextField.setText(resourceMap.getString("aOSJTextField.text")); // NOI18N
        aOSJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        aOSJTextField.setName("aOSJTextField"); // NOI18N
        aOSJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(aOSJTextField, gridBagConstraints);

        aOSJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        aOSJLabel.setText(resourceMap.getString("aOSJLabel.text")); // NOI18N
        aOSJLabel.setName("aOSJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(aOSJLabel, gridBagConstraints);

        regionJTextField.setEditable(false);
        regionJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        regionJTextField.setText(resourceMap.getString("regionJTextField.text")); // NOI18N
        regionJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        regionJTextField.setName("regionJTextField"); // NOI18N
        regionJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(regionJTextField, gridBagConstraints);

        regionJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        regionJLabel.setText(resourceMap.getString("regionJLabel.text")); // NOI18N
        regionJLabel.setName("regionJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(regionJLabel, gridBagConstraints);

        nextUpDatedAtJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nextUpDatedAtJLabel.setText(resourceMap.getString("nextUpDatedAtJLabel.text")); // NOI18N
        nextUpDatedAtJLabel.setName("nextUpDatedAtJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(nextUpDatedAtJLabel, gridBagConstraints);

        nextUpDatedAtJTextField.setEditable(false);
        nextUpDatedAtJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        nextUpDatedAtJTextField.setText(resourceMap.getString("nextUpDatedAtJTextField.text")); // NOI18N
        nextUpDatedAtJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        nextUpDatedAtJTextField.setName("nextUpDatedAtJTextField"); // NOI18N
        nextUpDatedAtJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(nextUpDatedAtJTextField, gridBagConstraints);

        lastUpDatedAtJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lastUpDatedAtJLabel.setText(resourceMap.getString("lastUpDatedAtJLabel.text")); // NOI18N
        lastUpDatedAtJLabel.setName("lastUpDatedAtJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(lastUpDatedAtJLabel, gridBagConstraints);

        lastUpDatedAtJTextField.setEditable(false);
        lastUpDatedAtJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        lastUpDatedAtJTextField.setText(resourceMap.getString("lastUpDatedAtJTextField.text")); // NOI18N
        lastUpDatedAtJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        lastUpDatedAtJTextField.setName("lastUpDatedAtJTextField"); // NOI18N
        lastUpDatedAtJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(lastUpDatedAtJTextField, gridBagConstraints);

        currentStateJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        currentStateJLabel.setText(resourceMap.getString("currentStateJLabel.text")); // NOI18N
        currentStateJLabel.setName("currentStateJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(currentStateJLabel, gridBagConstraints);

        currentStateJTextField.setEditable(false);
        currentStateJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        currentStateJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        currentStateJTextField.setName("currentStateJTextField"); // NOI18N
        currentStateJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(currentStateJTextField, gridBagConstraints);

        corpUseJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        corpUseJLabel.setText(resourceMap.getString("corpUseJLabel.text")); // NOI18N
        corpUseJLabel.setName("corpUseJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(corpUseJLabel, gridBagConstraints);

        corpUseJTextField.setEditable(false);
        corpUseJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        corpUseJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        corpUseJTextField.setName("corpUseJTextField"); // NOI18N
        corpUseJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(corpUseJTextField, gridBagConstraints);

        allianceUseJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        allianceUseJLabel.setText(resourceMap.getString("allianceUseJLabel.text")); // NOI18N
        allianceUseJLabel.setName("allianceUseJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(allianceUseJLabel, gridBagConstraints);

        allianceUseJTextField.setEditable(false);
        allianceUseJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        allianceUseJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        allianceUseJTextField.setName("allianceUseJTextField"); // NOI18N
        allianceUseJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(allianceUseJTextField, gridBagConstraints);

        aOSSJTextField.setEditable(false);
        aOSSJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aOSSJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        aOSSJTextField.setName("aOSSJTextField"); // NOI18N
        aOSSJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(aOSSJTextField, gridBagConstraints);

        aOSSJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        aOSSJLabel.setText(resourceMap.getString("aOSSJLabel.text")); // NOI18N
        aOSSJLabel.setName("aOSSJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(aOSSJLabel, gridBagConstraints);

        aOWTJTextField.setEditable(false);
        aOWTJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aOWTJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        aOWTJTextField.setName("aOWTJTextField"); // NOI18N
        aOWTJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(aOWTJTextField, gridBagConstraints);

        aOWTJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        aOWTJLabel.setText(resourceMap.getString("aOWTJLabel.text")); // NOI18N
        aOWTJLabel.setName("aOWTJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(aOWTJLabel, gridBagConstraints);

        aOAJTextField.setEditable(false);
        aOAJTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        aOAJTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        aOAJTextField.setName("aOAJTextField"); // NOI18N
        aOAJTextField.setPreferredSize(new java.awt.Dimension(175, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 10);
        mainPosDetailsInnerJPanel.add(aOAJTextField, gridBagConstraints);

        aOAJLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        aOAJLabel.setText(resourceMap.getString("aOAJLabel.text")); // NOI18N
        aOAJLabel.setName("aOAJLabel"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 2);
        mainPosDetailsInnerJPanel.add(aOAJLabel, gridBagConstraints);

        mainPosDetailsOuterJPanel.add(mainPosDetailsInnerJPanel, java.awt.BorderLayout.NORTH);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        mainJPanel.add(mainPosDetailsOuterJPanel, gridBagConstraints);

        buttonsOuterJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonsOuterJPanel.setName("buttonsOuterJPanel"); // NOI18N
        buttonsOuterJPanel.setLayout(new java.awt.BorderLayout());

        buttonsInnerJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("buttonsInnerJPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, resourceMap.getFont("buttonsInnerJPanel.border.titleFont"), resourceMap.getColor("buttonsInnerJPanel.border.titleColor"))); // NOI18N
        buttonsInnerJPanel.setName("buttonsInnerJPanel"); // NOI18N
        buttonsInnerJPanel.setLayout(new java.awt.GridBagLayout());

        addMonitorToList.setText(resourceMap.getString("addMonitorToList.text")); // NOI18N
        addMonitorToList.setEnabled(saveButtonState);
        addMonitorToList.setName("addMonitorToList"); // NOI18N
        addMonitorToList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMonitorToListActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonsInnerJPanel.add(addMonitorToList, gridBagConstraints);

        refreshPosDetails.setText(resourceMap.getString("refreshPosDetails.text")); // NOI18N
        refreshPosDetails.setName("refreshPosDetails"); // NOI18N
        refreshPosDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshPosDetailsActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        buttonsInnerJPanel.add(refreshPosDetails, gridBagConstraints);

        buttonsOuterJPanel.add(buttonsInnerJPanel, java.awt.BorderLayout.NORTH);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        mainJPanel.add(buttonsOuterJPanel, gridBagConstraints);

        jScrollPane1.setViewportView(mainJPanel);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addMonitorToListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMonitorToListActionPerformed
        String message, title;
        //Ask for name to give this session
        message = "Please enter a name to save the monitor session under.";
        title = "Enter Monitor Session Name.";
        monitorName = JOptionPane.showInputDialog(this, message, title, JOptionPane.QUESTION_MESSAGE);

        String[] apiDetails = new String[5];
        apiDetails[0] = apiKey;
        apiDetails[1] = userID;
        apiDetails[2] = characterID;
        apiDetails[3] = structureID;
        apiDetails[4] = monitorName;

        //Check if it already exists
        if(parentWindow.savedMonitorSessions.contains(monitorName))
        {
            //Ask if they wish to override or cancel
            java.lang.Object[] options = {"Override", "Cancel"};
            int n = JOptionPane.showOptionDialog(
                null,
                "Do you wish to override a previously saved monitor session?",
                "Override saved monitor session?", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]
            );
            if(n == 0) //Then OverRide
            {
                //Do the override
                if(database.updateSavedSession(apiDetails, posDetails, posList))
                {
                    JOptionPane.showMessageDialog(this,
                    "Monitor Session Successfuly Saved",
                    "Successful Session Save", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(this,
                    "Monitor Session Failed To Save",
                    "Failed Session Save", JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
            else { return; } //Else Cancel
        }

        //IF no duplicate name, then save the details
        //Then Lastly save name in DefaultListModel on main window, if it was successful.
        if(database.saveMonitorSession(apiDetails, posDetails, posList))
        {
            parentWindow.savedMonitorSessions.addElement(monitorName);
            JOptionPane.showMessageDialog(this,"Monitor Session Successfuly Saved",
            "Successful Session Save", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Monitor Session Failed To Save",
            "Failed Session Save", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addMonitorToListActionPerformed

    private void refreshPosDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshPosDetailsActionPerformed
        //Check that the current date and time is older than the cached until date time.
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        Date currentDate = new Date();
        Date cachedUntil = posDetails.getCachedUntil();

        if(currentDate.before(cachedUntil)) //then need to wait longer
        {
            //Inform User They Need To Wait More
            long diff = cachedUntil.getTime() - currentDate.getTime();
            long diffMinutes = diff / (60 * 1000);
            long minutes = diffMinutes % 60;
            long hours = diffMinutes / 60;
            JOptionPane.showMessageDialog(this,"You Need To Wait: "+hours+" hours, "+minutes+" minutes.",
            "Need To Wait Longer", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Query API for new details.
        StarbaseListResponse listResult = EvePosMad_Api.getStarbaseList
        (
            apiKey,
            Long.parseLong(characterID),
            Integer.parseInt(userID)
        );

        StarbaseDetailResponse detailResult = EvePosMad_Api.getStarbaseDetail
        (
            apiKey,
            Long.parseLong(characterID),
            Integer.parseInt(userID),
            Long.parseLong(structureID)
        );

        //Update stored data in class
        ApiStarbase newPosState = new ApiStarbase();
        for(ApiStarbase pos : listResult.getStarbases())
        {
            if(pos.getItemID() == Long.parseLong(structureID))
            {
                newPosState = pos;
            }
        }

        for(ApiStarbase pos : posList.getStarbases())
        {
            if(pos.getItemID() == Long.parseLong(structureID))
            {
                pos.setState(newPosState.getState());
                pos.setOnlineTimestamp(newPosState.getOnlineTimestamp());
                pos.setStateTimestamp(newPosState.getStateTimestamp());
            }
        }

        posDetails.setAllowAllianceMembers(detailResult.isAllowAllianceMembers());
        posDetails.setAllowCorporationMembers(detailResult.isAllowCorporationMembers());
        posDetails.setCachedUntil(detailResult.getCachedUntil());
        posDetails.setCurrentTime(detailResult.getCurrentTime());
        posDetails.setOnAggression(detailResult.getOnAggression());
        posDetails.setOnCorporationWar(detailResult.getOnCorporationWar());
        posDetails.setOnStandingDrop(detailResult.getOnStandingDrop());
        posDetails.setOnStatusDrop(detailResult.getOnStatusDrop());
        posDetails.getFuelMap().clear();
        posDetails.getFuelMap().putAll(detailResult.getFuelMap());

        //Update views
        this.setupMainPosStats(Long.parseLong(structureID));
        this.updateFuelStats();

        //Update database saved session if allready saved.
        if(parentWindow.savedMonitorSessions.contains(monitorName))
        {
            String[] apiDetails = new String[5];
            apiDetails[0] = apiKey;
            apiDetails[1] = userID;
            apiDetails[2] = characterID;
            apiDetails[3] = structureID;
            apiDetails[4] = monitorName;
            if(database.updateSavedSession(apiDetails, posDetails, posList)){}
            else
            {
                JOptionPane.showMessageDialog(this,"Monitor Session Failed To Save After Refreshing Data",
                "Failed Session Save", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_refreshPosDetailsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aOAJLabel;
    private javax.swing.JTextField aOAJTextField;
    private javax.swing.JLabel aOSJLabel;
    private javax.swing.JTextField aOSJTextField;
    private javax.swing.JLabel aOSSJLabel;
    private javax.swing.JTextField aOSSJTextField;
    private javax.swing.JLabel aOWTJLabel;
    private javax.swing.JTextField aOWTJTextField;
    private javax.swing.JButton addMonitorToList;
    private javax.swing.JLabel allianceUseJLabel;
    private javax.swing.JTextField allianceUseJTextField;
    private javax.swing.JPanel buttonsInnerJPanel;
    private javax.swing.JPanel buttonsOuterJPanel;
    private javax.swing.JLabel charterJLabel;
    private javax.swing.JTextField charterQuantityJTextField;
    private javax.swing.JTextField charterTimeJTextField;
    private javax.swing.JLabel constellationJLabel;
    private javax.swing.JTextField constellationJTextField;
    private javax.swing.JLabel coolantJLabel;
    private javax.swing.JTextField coolantQuantityJTextField;
    private javax.swing.JTextField coolantTimeJTextField;
    private javax.swing.JLabel corpUseJLabel;
    private javax.swing.JTextField corpUseJTextField;
    private javax.swing.JPanel currentFuelLevelsInnerJPanel;
    private javax.swing.JPanel currentFuelLevelsOuterJPanel;
    private javax.swing.JLabel currentStateJLabel;
    private javax.swing.JTextField currentStateJTextField;
    private javax.swing.JLabel enrichedUraniumJLabel;
    private javax.swing.JTextField enrichedUraniumQuantityJTextField;
    private javax.swing.JTextField enrichedUraniumTimeJTextField;
    private javax.swing.JSeparator firstJSeparator;
    private javax.swing.JLabel fuelNameJLabel;
    private javax.swing.JLabel fuelQuantityJLabel;
    private javax.swing.JLabel fuelTimeJLabel;
    private javax.swing.JLabel heavyWaterJLabel;
    private javax.swing.JTextField heavyWaterQuantityJTextField;
    private javax.swing.JTextField heavyWaterTimeJTextField;
    private javax.swing.JLabel isotopesJLabel;
    private javax.swing.JTextField isotopesQuantityJTextField;
    private javax.swing.JTextField isotopesTimeJTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lastUpDatedAtJLabel;
    private javax.swing.JTextField lastUpDatedAtJTextField;
    private javax.swing.JLabel liquidOzoneJLabel;
    private javax.swing.JTextField liquidOzoneQuantityJTextField;
    private javax.swing.JTextField liquidOzoneTimeJTextField;
    private javax.swing.JPanel mainJPanel;
    private javax.swing.JPanel mainPosDetailsInnerJPanel;
    private javax.swing.JPanel mainPosDetailsOuterJPanel;
    private javax.swing.JLabel mechanicalPartsJLabel;
    private javax.swing.JTextField mechanicalPartsQuantityJTextField;
    private javax.swing.JTextField mechanicalPartsTimeJTextField;
    private javax.swing.JLabel nextUpDatedAtJLabel;
    private javax.swing.JTextField nextUpDatedAtJTextField;
    private javax.swing.JLabel onlinedDTJLabel;
    private javax.swing.JTextField onlinedDTJTextField;
    private javax.swing.JLabel oxygenJLabel;
    private javax.swing.JTextField oxygenQuantityJTextFiel;
    private javax.swing.JTextField oxygenTimeJTextField;
    private javax.swing.JButton refreshPosDetails;
    private javax.swing.JLabel regionJLabel;
    private javax.swing.JTextField regionJTextField;
    private javax.swing.JLabel roboticsJLabel;
    private javax.swing.JTextField roboticsQuantityJTextField;
    private javax.swing.JTextField roboticsTimeJTextField;
    private javax.swing.JSeparator secondJSeparator;
    private javax.swing.JLabel stateDTJLabel;
    private javax.swing.JTextField stateDTJTextField;
    private javax.swing.JLabel strontiumJLabel;
    private javax.swing.JTextField strontiumQuantityJTextField;
    private javax.swing.JTextField strontiumTimeJTextField;
    private javax.swing.JLabel systemMoonJLabel;
    private javax.swing.JTextField systemMoonJTextField;
    private javax.swing.JLabel systemSecurityLevelJLabel;
    private javax.swing.JTextField systemSecurityLevelJTextField;
    private javax.swing.JSeparator thirdJSeparator;
    private javax.swing.JLabel typeJLabel;
    private javax.swing.JTextField typeJTextField;
    private javax.swing.JLabel universeJLabel;
    private javax.swing.JTextField universeJTextField;
    // End of variables declaration//GEN-END:variables

    private EvePosMad_Database database;
    private EvePosMad_View parentWindow;
    private String apiKey, userID, characterID, structureID, monitorName;
    private StarbaseDetailResponse posDetails;
    private StarbaseListResponse posList;
    private boolean saveButtonState;
    private long posTypeID;
}