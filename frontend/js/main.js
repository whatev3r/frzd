var data;

var delayTimer;

var deadline = "04.11.2022";

function doSearch(text) {
  clearTimeout(delayTimer);
  delayTimer = setTimeout(function () {

    let xhr = new XMLHttpRequest();
     xhr.open("POST", "http://195.24.67.169:9090/main/");
    //xhr.open("POST", "http://localhost:9090/main/");

    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "text/plain");

    xhr.onload = () =>
    {
      if (xhr.responseText != "[]") {
        ngForFunctionality(JSON.parse(xhr.responseText))
      }
    };
    xhr.send(text);

  }, 1000); // Will do the ajax stuff after 1000 ms, or 1 s
}

function clearSearch() {
  document.getElementById("searchText").value = "";
  setDataToList(`<li ><div class="fuck-rostelecom">Если начала отображаться реклама, то говорим спасибо Ростелекому. Подробнее <a href="https://www.opennet.ru/opennews/art.shtml?num=52444">тут</a>, либо в гугле по запросу "ростелеком http реклама".</div></li>`);
}

function ngForFunctionality(data) {
  let value = '';
  data.forEach((post) => {
    value += `<li ><div class="question">${post.question}</div></li><br/><li><div class="answer">${post.answer}</div><li><br/>`;
  });
  value += `<li ><div class="fuck-rostelecom">Если начала отображаться реклама, то говорим спасибо Ростелекому. Подробнее <a href="https://www.opennet.ru/opennews/art.shtml?num=52444">тут</a>, либо в гугле по запросу "ростелеком http реклама".</div></li>`;
  setDataToList(value);
};

function setDataToList(value){
  el = document.getElementById('list');
  el.innerHTML = value;
}

function setTitle() {
  document.getElementById("title").innerHTML = `Сервис будет доступен до ${deadline}`;
  setDataToList(`<li ><div class="fuck-rostelecom">Если начала отображаться реклама, то говорим спасибо Ростелекому. Подробнее <a href="https://www.opennet.ru/opennews/art.shtml?num=52444">тут</a>, либо в гугле по запросу "ростелеком http реклама".</div></li>`);
}

function setWhyBlock() {
  document.getElementById("why_block").innerHTML = `Сервис крутится на виртуальном сервере VDS от nic.ru. Аренда сервера проплачена до ${deadline}`;
}

function initAbout() {
  document.getElementById("title").innerHTML = `Сервис будет доступен до ${deadline}`;
  setWhyBlock();
}
