function openTab(evt, tabName) {
    var i, tabcontent, tablinks;

    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}

function addOption(oListbox, text, value, isDefaultSelected, isSelected)
{
    let oOption = document.createElement("option");
    oOption.appendChild(document.createTextNode(text));
    oOption.setAttribute("value", value);

    if (isDefaultSelected) oOption.defaultSelected = true;
    else if (isSelected) oOption.selected = true;

    oListbox.appendChild(oOption);
}

async function getEntries() {
    let entryBlockId = document.getElementById("entryInfoBlock");
    entryBlockId.hidden = true;
    let dateControl = document.getElementById("choiceDate");
    let state = document.getElementById("entryState").value;
    let addr = "/entries?"
    if (dateControl.valueAsDate != null) {
        addr += "date=" + dateControl.value;
    }
    addr += "&state=" + state;
    let response = await fetch(addr);
    let entries = await response.json();

    const entryId = document.getElementById("entries");
    entryId.options.length = 0;
    for (key in entries) {
        addOption(entryId,entries[key],key);
    }
    if (entryId.options.length == 0) {
        entryId.hidden = true;
    } else {
        entryId.hidden = false;
        entryId.setAttribute("size",entryId.options.length);
    }
}

async function getComplaints() {
    let complaintBlockId = document.getElementById("complaintInfoBlock");
    complaintBlockId.hidden = true;
    let state = document.getElementById("complaintState").value;
    let addr = "/complaints?"
    addr += "state=" + state;
    let response = await fetch(addr);
    let complaints = await response.json();

    const complaintId = document.getElementById("complaints");
    complaintId.options.length = 0;
    for (key in complaints) {
        addOption(complaintId,complaints[key],key);
    }
    if (complaintId.options.length == 0) {
        complaintId.hidden = true;
    } else {
        complaintId.hidden = false;
        complaintId.setAttribute("size",complaintId.options.length);
    }
}

async function openEntry() {
    let addr = "/entryInfo?id=" + document.getElementById("entries").value;
    let response = await fetch(addr);
    let entryInfo = await response.json();
    let entryBlockId = document.getElementById("entryInfoBlock");
    let entryInfoId = document.getElementById("entryInfo");
    entryInfoId.value = "";
    entryInfoId.value += "Дата записи: " + entryInfo["date"] +"\n";
    var myDate = new Date(entryInfo["time"]);
    entryInfoId.value += "Время записи: "
        + formatTime(myDate) +"\n";
    entryInfoId.value += "Мастер: " + entryInfo["master"]["name"] +"\n";
    entryInfoId.value += "Клиент: " + entryInfo["client"]["name"] + " "
        + entryInfo["client"]["phone"] + "\n";
    entryInfoId.value += "Услуги: " + "\n";
    for (let i = 0; i < entryInfo["services"].length; i++) {
        entryInfoId.value += "\t" + entryInfo["services"][i]["name"] + "\n";
    }
    entryInfoId.value += "Статус записи: " + entryInfo["state"] +"\n";
    let transferButton = document.getElementById("transferForm");
    transferButton.hidden = false;
    if (entryInfo["state"] != "OPENED") {
        transferButton.hidden = true;
    }
    entryBlockId.hidden = false;
}

function formatTime(d) {
    return (d.getHours().toString().length==2
        ? d.getHours().toString():"0" + d.getHours().toString())
        + ":" + ((parseInt(d.getMinutes()/5)*5).toString().length==2
            ?(parseInt(d.getMinutes()/5)*5).toString()
            :"0"+(parseInt(d.getMinutes()/5)*5).toString())
}

async function openComplaint() {
    let addr = "/complaintInfo?id=" + document.getElementById("complaints").value;
    let response = await fetch(addr);
    let complaintInfo = await response.json();
    let complaintBlockId = document.getElementById("complaintInfoBlock");
    let complaintInfoId = document.getElementById("complaintInfo");
    complaintInfoId.value = "";
    let myDate = new Date(complaintInfo["entry"]["time"]);
    complaintInfoId.value += "Дата записи: " + complaintInfo["entry"]["date"]
        + " " + formatTime(myDate) + "\n";
    complaintInfoId.value += "Мастер: " + complaintInfo["entry"]["master"]["name"] +"\n";
    complaintInfoId.value += "Клиент: " + complaintInfo["entry"]["client"]["name"] + " "
        + complaintInfo["entry"]["client"]["phone"] + "\n";
    complaintInfoId.value += "Услуги: " + "\n";
    for (let i = 0; i < complaintInfo["entry"]["services"].length; i++) {
        complaintInfoId.value += "\t" + complaintInfo["entry"]["services"][i]["name"] + "\n";
    }
    complaintInfoId.value += "Статус жалобы: " + complaintInfo["state"] +"\n";
    let transferButton = document.getElementById("transferComplaintForm");
    transferButton.hidden = false;
    if (complaintInfo["state"] != "OPENED") {
        transferButton.hidden = true;
    }
    complaintBlockId.hidden = false;
    let complaintText = document.getElementById("complaintText");
    complaintText.value = complaintInfo["text"];
    let complaintExplanatory = document.getElementById("complaintExplanatory");
    complaintExplanatory.value = complaintInfo["explanatory"];
    let complaintDecisionMaster = document.getElementById("complaintDecisionMaster");
    complaintDecisionMaster.value = complaintInfo["decisionMaster"];
    let complaintDecisionClient = document.getElementById("complaintDecisionClient");
    complaintDecisionClient.value = complaintInfo["decisionClient"];
}

async function transferEntry() {
    let addr = "/admin/transferEntry?id=" + document.getElementById("entries").value;
    await fetch(addr)
        .then(response => {
            if (response.status == 200) {
                alert("Запись передана мастеру");
                let entryBlockId = document.getElementById("entryInfoBlock");
                entryBlockId.hidden = true;
                getEntries();
            } else {
                alert("Что-то пошло не так. Попробуйте еще раз");
            }
        });
}

async function transferComplaint() {
    let addr = "/admin/transferComplaint?id=" +
        document.getElementById("complaints").value;
    await fetch(addr)
        .then(response => {
            if (response.status == 200) {
                alert("Жалоба передана мастеру");
                let complaintBlockId = document.getElementById("complaintInfoBlock");
                complaintBlockId.hidden = true;
                getComplaints();
            } else {
                alert("Что-то пошло не так. Попробуйте еще раз");
            }
        });
}

function config() {
    let currentDate = new Date;
    document.getElementById('choiceDate')
        .setAttribute("value", currentDate.toISOString().slice(0, 10));
    getEntries();
    getComplaints();
}

config();

var f = document.getElementById('transferForm')

f.addEventListener('submit', function(e) {
    e.preventDefault();
    transferEntry();
})

var f = document.getElementById('transferComplaintForm')

f.addEventListener('submit', function(e) {
    e.preventDefault();
    transferComplaint();
})