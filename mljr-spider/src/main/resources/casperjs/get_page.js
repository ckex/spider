var brower = require('casper').create();
var page;

brower.start();

brower.thenOpen('http://www.tianyancha.com/company/30361073');

brower.then(function getPage() {
    page = brower.evaluate(function getHtmlFromPage() {
        return $("html").html();
    });
});

brower.then(function outputHtml() {
    console.log(page);
    brower.exit();
});

brower.run();
