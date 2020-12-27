package beauty_app.controllers;

public class MasterController {/*implements Initializable {
    Integer masterId;
        @FXML
        private ListView<Entry> entryList;

        @FXML
        private DatePicker choiceDate;

        @FXML
        private ListView<Complaint> closedComplaintsList;

        @FXML
        private ListView<Complaint> openedComplaintsList;

        @FXML
        private ListView<Complaint> waitingComplaintsList;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
        }

        public void init(Integer masterId) {
            this.masterId = masterId;
            configChoiceDate();
            configEntryList();
            configClosedComplaintsList();
            configWaitingComplaintsList();
            configOpenComplaintsList();
        }

    public void upd(){
        setClosedComplaintList();
        setEntryList();
        setOpenedComplaintList();
        setWaitingComplaintList();
    }

        public void exit(){
            BeautyApp.setClient();
        }

        private void configEntryList(){
            setEntryList();
            entryList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Entry>() {

                @Override
                public void changed(ObservableValue<? extends Entry> observable, Entry oldValue, Entry newValue) {
                    if(newValue != null){
                        Parent root;
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            URL xmlUrl = BeautyApp.class.getResource("/webapp.view/EntryInfo.fxml");
                            loader.setLocation(xmlUrl);
                            root = loader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            EntryInfoController c = loader.getController();
                            c.initForMaster(newValue);
                            stage.show();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        private void configOpenComplaintsList() {
            setOpenedComplaintList();
            openedComplaintsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Complaint>() {

                @Override
                public void changed(ObservableValue<? extends Complaint> observable, Complaint oldValue, Complaint newValue) {
                    if(newValue != null){
                        goToComplaintInfo(newValue);
                    }
                }
            });
        }

        private void configClosedComplaintsList() {
            setClosedComplaintList();
            closedComplaintsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Complaint>() {

                @Override
                public void changed(ObservableValue<? extends Complaint> observable, Complaint oldValue, Complaint newValue) {
                    if(newValue != null){
                        goToComplaintInfo(newValue);
                    }
                }
            });
        }

        private void configWaitingComplaintsList() {
            setWaitingComplaintList();
            waitingComplaintsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Complaint>() {

                @Override
                public void changed(ObservableValue<? extends Complaint> observable, Complaint oldValue, Complaint newValue) {
                    if(newValue != null){
                        goToComplaintInfo(newValue);
                    }
                }
            });
        }

    private void goToComplaintInfo(Complaint newValue){
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = BeautyApp.class.getResource("/webapp.view/ComplaintInfo.fxml");
            loader.setLocation(xmlUrl);
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            ComplaintInfoController c = loader.getController();
            c.initForMaster(newValue);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

        private void setEntryList(){
            ObservableList<Entry> entries = Facade.getInstance().getEntriesForMaster(choiceDate.getValue(), masterId);
            entryList.setItems(entries);
        }

        private void setOpenedComplaintList(){
            ObservableList<Complaint> complaints = Facade.getInstance().getWaitingExplComplaints(masterId);
            openedComplaintsList.setItems(complaints);
        }

        private void setClosedComplaintList(){
            ObservableList<Complaint> complaints = Facade.getInstance().getClosedComplaints(masterId);
            closedComplaintsList.setItems(complaints);
        }

        private void setWaitingComplaintList(){
            ObservableList<Complaint> complaints = Facade.getInstance().getWaitingDecComplaints(masterId);
            waitingComplaintsList.setItems(complaints);
        }

        private void configChoiceDate(){
            choiceDate.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();

                    setDisable(empty || date.compareTo(today) < 0 );
                }
            });
            choiceDate.setValue(LocalDate.now());

            choiceDate.valueProperty().addListener((ov, oldValue, newValue) -> {
                setEntryList();
            });
        }*/
}