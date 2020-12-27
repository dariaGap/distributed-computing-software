package beauty_app.controllers;

import beauty_app.business_logic.Entry;

public class EntryInfoController {
    Entry entry;

   /* @FXML
    private TextArea entryInfo;

    @FXML
    private Button transfer;

    public void initForMaster(Entry entry){
        transfer.setVisible(false);
        init(entry);
    }

    public void init(Entry entry){
        this.entry = entry;
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        entryInfo.clear();
        entryInfo.appendText("Дата: " + format.format(entry.getDate().getTime()) + "\n");
        entryInfo.appendText("Клиент: " + entry.getClient() + "\n");
        entryInfo.appendText("Мастер: " + entry.getMaster() + "\n");
        entryInfo.appendText("Услуги: \n");
        for(Service s: entry.getServices()){
            entryInfo.appendText("\t" + s + "\n");
        }
        entryInfo.appendText("Статус: " + entry.getState().toString() + "\n");
    }

    public void transfer(){
        String info;
        if(entry.getState().equals(Entry.EntryState.OPENED)) {
            Boolean res = Facade.getInstance().transferEntry(entry);
            if (res)
                info = "Запись передана мастеру!";
            else
                info = "Произошла ошибка. Попробуйте еще раз!";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запись");
            alert.setHeaderText(null);
            alert.setContentText(info);
            alert.showAndWait();
            init(entry);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запись");
            alert.setHeaderText(null);
            alert.setContentText("Невозможно передать запись мастеру!");
            alert.showAndWait();
        }
    }*/
}
