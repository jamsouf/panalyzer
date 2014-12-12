/**
 * Initialize the app
 * and routings and
 * run it!
 */
(function($) {
    init();
    
    var title = 'panalyzer / ',
        delay = 100,
        app = $.sammy(function() {
    
        this.get('#/overview', function() {
            document.title = title + 'overview';
            $("#overview").show(delay);
            $("#folders").hide(delay);
            $("#files").hide(delay);
            $("#clk-overview").addClass('active');
            $("#clk-folders").removeClass('active');
            $("#clk-files").removeClass('active');
        });
        
        this.get('#/folders', function() {
            document.title = title + 'folders';
            $("#overview").hide(delay);
            $("#folders").show(delay);
            $("#files").hide(delay);
            $("#clk-overview").removeClass('active');
            $("#clk-folders").addClass('active');
            $("#clk-files").removeClass('active');
        });
        
        this.get('#/files', function() {
            document.title = title + 'files';
            $("#overview").hide(delay);
            $("#folders").hide(delay);
            $("#files").show(delay);
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
    write('dt.folders', nbr(Data.folders));
    write('dt.files', nbr(Data.files));
    write('dt.loc', nbr(Data.linesOfCode));
    
    generateTopFileExtChart();
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
        series: [{
            type: 'pie',
            name: 'File types',
            data: [
                [Data.top5FileExt[0], Data.top5FileExtCount[0]],
                [Data.top5FileExt[1], Data.top5FileExtCount[1]],
                [Data.top5FileExt[2], Data.top5FileExtCount[2]],
                [Data.top5FileExt[3], Data.top5FileExtCount[3]],
                [Data.top5FileExt[4], Data.top5FileExtCount[4]]
            ]
        }],
        colors: ['#354F5D', '#34B39E', '#E1584D', '#EEA19B', '#BCBCBC']
    });
}