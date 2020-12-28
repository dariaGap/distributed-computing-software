function addOption(oListbox, text, value, isDefaultSelected, isSelected)
{
    let oOption = document.createElement("option");
    oOption.appendChild(document.createTextNode(text));
    oOption.setAttribute("value", value);

    if (isDefaultSelected) oOption.defaultSelected = true;
    else if (isSelected) oOption.selected = true;

    oListbox.appendChild(oOption);
}

async function getPossibleTime() {
    const selectedMaster = document.getElementById("choiceMaster").selectedIndex;
    const selectedTime = document.getElementById("choiceTime").value;
    let dateControl = document.querySelector('input[type="date"]');
    let addr = "/entry/free-time?"
    if (selectedMaster >= 0) {
        addr += "master=" + document.getElementById("choiceMaster").value;
    }
    if (dateControl.valueAsDate != null) {
        addr += "&date=" + dateControl.value;
    }

    let response = await fetch(addr);

    let times = await response.json();

    const timeId = document.getElementById("choiceTime");
    timeId.options.length = 0;
    for (let i = 0; i < times.length; i++) {
        let isSelected = false;
        if (selectedTime == times[i]) {
            isSelected = true
        }
        addOption(timeId, times[i],times[i],false,isSelected);
    }
}

async function getMasters() {
    const selectedType = document.getElementById("choiceService").selectedIndex;
    const selectedMaster = document.getElementById("choiceMaster").value;
    let addr = "/entry/masters"

    if (selectedType >= 0) {
        addr += "?services=" + getSelectedServices();
    }
    let response = await fetch(addr);

    let masters = await response.json();
    const masterId = document.getElementById("choiceMaster");
    masterId.options.length = 0;
    for (key in masters) {
        let isSelected = false;
        if (selectedMaster == key) {
            isSelected = true;
        }
        addOption(masterId,masters[key],key,false,isSelected);
    }
}

async function getServices() {
    const selectedMaster = document.getElementById("choiceMaster").selectedIndex;
    const selectedServices = document.getElementById("choiceService").selectedIndex;
    let selected = [];
    let addr = "/entry/services?"
    if (selectedMaster >= 0) {
        addr += "master=" + document.getElementById("choiceMaster").value;
        addr += "&"
    }
    if (selectedServices >= 0) {
        selected = getSelectedServices()
        addr += "services=" + selected;
    }
    let response = await fetch(addr);

    let services = await response.json();
    const servicesId = document.getElementById("choiceService");
    servicesId.options.length = 0;
    for (key in services) {
        let isSelected = false;
        if (selected.includes(key)) {
            isSelected = true;
        }
        addOption(servicesId,services[key],key,false,isSelected);
    }
}

function masterSelected() {
    getServices();
    getPossibleTime();
}

function getSelectedServices() {
    let values = document.getElementById("choiceService").options;
    var result = [];
    for (var i=0, iLen=values.length; i<iLen; i++) {
        opt = values[i];
        if (opt.selected) {
            result.push(opt.value);
        }
    }
    return result;
}

function serviceSelected() {
    getMasters();
    getServices();
}

function configureEntry() {
    let currentDate = new Date;
    currentDate.setDate(currentDate.getDate() + 1);
    document.getElementById('choiceDate')
        .setAttribute("min", currentDate.toISOString().slice(0, 10));
    document.getElementById('choiceDate')
        .setAttribute("value", currentDate.toISOString().slice(0, 10));
    getServices();
    getPossibleTime();
    getMasters();
}

async function go() {
    let date = document.querySelector('input[type="date"]').value;
    let time = document.getElementById("choiceTime").value;
    let master = document.getElementById("choiceMaster").value;
    let services = getSelectedServices();
    let name = document.getElementById("name").value;
    let phone = document.getElementById("phone").value;

    let addr = "/entry/save?date=" + date
        + "&time=" + time + "&master=" + master + "&services=" + services
        + "&name=" + name + "&phone=" + phone;

    await fetch(addr)
        .then(response => {
            if (response.status == 200) {
                alert("Вы успешно записаны");
            } else {
                alert("Что-то пошло не так. Попробуйте еще раз");
            }
        });
    document.location.href = '/';
}

configureEntry();
var f = document.querySelector('form')

f.addEventListener('submit', function(e) {
    e.preventDefault();
    go();
})
