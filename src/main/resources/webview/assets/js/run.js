/**
 * Initialize the app
 * and routings and
 * run it!
 */
(function($) {
    init();
    
    var title = 'panalyzer / ',
        app = $.sammy(function() {
    
        this.get('#/overview', function() {
            document.title = title + 'overview';
            $("#overview").show();
            $("#folders").hide();
            $("#files").hide();
            $("#clk-overview").addClass('active');
            $("#clk-folders").removeClass('active');
            $("#clk-files").removeClass('active');
        });
        
        this.get('#/folders', function() {
            document.title = title + 'folders';
            $("#overview").hide();
            $("#folders").show();
            $("#files").hide();
            $("#clk-overview").removeClass('active');
            $("#clk-folders").addClass('active');
            $("#clk-files").removeClass('active');
        });
        
        this.get('#/files', function() {
            document.title = title + 'files';
            $("#overview").hide();
            $("#folders").hide();
            $("#files").show();
            $("#clk-overview").removeClass('active');
            $("#clk-folders").removeClass('active');
            $("#clk-files").addClass('active');
        });
    });
    
    $(function() {
        app.run('#/overview');
    });
})(jQuery);

/**
 * Fill the content
 * at startup with values
 */
function init () {
    write('dt.project', Data.project);
    write('dt.analyzed', Data.analyzed);
    write('dt.sizeTotal', Data.sizeTotal);
    write('dt.folders', nbr(Data.folders));
    write('dt.files', nbr(Data.files));
    write('dt.loc', nbr(Data.linesOfCode));
    generateTopFileExtChart();
    
    write('dt.folders2', nbr(Data.folders));
    generateTopLocFolders();
}

/**
 * Write a value into
 * the document
 */
function write(id, content) {
    document.getElementById(id).innerHTML = content;
}

/**
 * Format a number for
 * better readability
 */
function nbr(n) {
    return n.toLocaleString();
}

/**
 * Create the chart for the
 * allocation of file types
 */
function generateTopFileExtChart() {
    $('#topFileExt').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: null
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true
                },
                showInLegend: false
            }
        },
        credits: {
            enabled: false
        },
        series: [{
            type: 'pie',
            name: 'File types',
            data: [
                [Data.topFileExt[0], Data.topFileExtCount[0]],
                [Data.topFileExt[1], Data.topFileExtCount[1]],
                [Data.topFileExt[2], Data.topFileExtCount[2]],
                [Data.topFileExt[3], Data.topFileExtCount[3]],
                [Data.topFileExt[4], Data.topFileExtCount[4]]
            ]
        }],
        colors: ['#354F5D', '#34B39E', '#E1584D', '#EEA19B', '#BCBCBC']
    });
}

/**
 * Create the chart for the
 * most line of code in folders
 */
function generateTopLocFolders() {
    $('#topLocFolders').highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: null
        },
        xAxis: {
            categories: Data.topLocFolders,
            title: {
                text: null
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: null
            }
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y}</b></b>'
        },
        plotOptions: {
            bar: {
                showInLegend: false
            }
        },
        credits: {
            enabled: false
        },
        series: [{
            name: 'Lines of code',
            data: Data.topLocFoldersCount
        }],
        colors: ['#3E4A50']
    });
}