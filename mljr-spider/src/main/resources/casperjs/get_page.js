var casper = require('casper').create();
var page;

var url = casper.cli.args[0];

casper.start();

casper.thenOpen( url );

casper.then(function getPage() {
    page = casper.evaluate(function getHtmlFromPage() {
        return $("html").html();
    });
});

casper.then(function outputHtml() {
    console.log(page);
    casper.exit();
});

casper.run();