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
    initOverview();
    initFolders();
    initFiles();
}

/**
 * Create and insert the
 * data for the overview-site
 */
function initOverview() {
    var max = 85;
    write('d.project', cut(Data.project, max));
    write('d.analyzed', Data.analyzed);
    write('d.totalSize', size(Data.totalSize));
    /*************************************************************/
    write('d.totalFolders', nbr(Data.totalFolders));
    /*************************************************************/
    write('d.totalFiles', nbr(Data.totalFiles));
    /*************************************************************/
    write('d.totalLoc', nbr(Data.totalLoc));
    /*************************************************************/
    generateTopFilesExtChart();
}

/**
 * Create and insert the
 * data for the folders-site
 */
function initFolders() {
    var files = ' files';
    write('tff-value-1', Data.topFoldersFiles[0].value+files);
    write('tff-value-2', Data.topFoldersFiles[1].value+files);
    write('tff-value-3', Data.topFoldersFiles[2].value+files);
    write('tff-value-4', Data.topFoldersFiles[3].value+files);
    write('tff-value-5', Data.topFoldersFiles[4].value+files);
    var max = 50;
    write('tff-ident-1', cut(Data.topFoldersFiles[0].ident, max));
    write('tff-ident-2', cut(Data.topFoldersFiles[1].ident, max));
    write('tff-ident-3', cut(Data.topFoldersFiles[2].ident, max));
    write('tff-ident-4', cut(Data.topFoldersFiles[3].ident, max));
    write('tff-ident-5', cut(Data.topFoldersFiles[4].ident, max));
    /*************************************************************/
    write('d.totalFolders-2', nbr(Data.totalFolders));
    /*************************************************************/
    write('tfs-value-1', size(Data.topFoldersSize[0].value));
    write('tfs-value-2', size(Data.topFoldersSize[1].value));
    write('tfs-value-3', size(Data.topFoldersSize[2].value));
    max = 18;
    write('tfs-ident-1', cut(Data.topFoldersSize[0].ident, max));
    write('tfs-ident-2', cut(Data.topFoldersSize[1].ident, max));
    write('tfs-ident-3', cut(Data.topFoldersSize[2].ident, max));
    /*************************************************************/
    generateTopFoldersLocChart();
}

/**
 * Create and insert the
 * data for the files-site
 */
function initFiles() {
    write('d.totalFiles-2', nbr(Data.totalFiles));
    /*************************************************************/
    generateTopFilesSizeChart();
    /*************************************************************/
    generateTopFilesLocChart();
    /*************************************************************/
    generateTopExtLocChart();
    /*************************************************************/
    write('tflm-date-1', Data.topFilesLastMod[0].date);
    write('tflm-date-2', Data.topFilesLastMod[1].date);
    write('tflm-date-3', Data.topFilesLastMod[2].date);
    write('tflm-date-4', Data.topFilesLastMod[3].date);
    write('tflm-date-5', Data.topFilesLastMod[4].date);
    write('tflm-time-1', Data.topFilesLastMod[0].time);
    write('tflm-time-2', Data.topFilesLastMod[1].time);
    write('tflm-time-3', Data.topFilesLastMod[2].time);
    write('tflm-time-4', Data.topFilesLastMod[3].time);
    write('tflm-time-5', Data.topFilesLastMod[4].time);
    write('tflm-owner-1', Data.topFilesLastMod[0].owner);
    write('tflm-owner-2', Data.topFilesLastMod[1].owner);
    write('tflm-owner-3', Data.topFilesLastMod[2].owner);
    write('tflm-owner-4', Data.topFilesLastMod[3].owner);
    write('tflm-owner-5', Data.topFilesLastMod[4].owner);
    var max = 70;
    write('tflm-ident-1', file(cut(Data.topFilesLastMod[0].ident, max)));
    write('tflm-ident-2', file(cut(Data.topFilesLastMod[1].ident, max)));
    write('tflm-ident-3', file(cut(Data.topFilesLastMod[2].ident, max)));
    write('tflm-ident-4', file(cut(Data.topFilesLastMod[3].ident, max)));
    write('tflm-ident-5', file(cut(Data.topFilesLastMod[4].ident, max)));
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
 * Convert the byte value
 * into better readable format
 */
function size(bytes) {
    if(bytes == 0) return '0 Byte';
    var k = 1000;
    var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    var i = Math.floor(Math.log(bytes) / Math.log(k));
    return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}

/**
 * Cut the first parts of
 * a string if it's too long
 */
function cut(v, max) {
    if (v.length > max) {
        v = '[...]' + v.slice(-max);
    }
    return v;
}

/**
 * Highlight the file in
 * a path string
 */
function file(str) {
    var n = str.lastIndexOf('/');
    var path = str.substring(0, n + 1);
    var file = str.substring(n + 1);
    return '<span class="path-start">'+path+'</span><span class="path-end">'+file+'</span>';
}

/**
 * Highlight the last folder
 * in a path string
 */
function folder(str) {
    if (str.slice(-1) == '/') {
        str = str.substring(0, str.length - 1);
    }
    var n = str.lastIndexOf('/');
    var path = str.substring(0, n + 1);
    var folder = str.substring(n + 1);
    return '<span class="path-start">'+path+'</span><span class="path-end">'+folder+'/</span>';
}

/**
 * Create the chart for the
 * allocation of file types
 */
function generateTopFilesExtChart() {
    $('#topFilesExt').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: null
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b> ({point.y})'
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
                [Data.topFilesExt[0].ident, Data.topFilesExt[0].value],
                [Data.topFilesExt[1].ident, Data.topFilesExt[1].value],
                [Data.topFilesExt[2].ident, Data.topFilesExt[2].value],
                [Data.topFilesExt[3].ident, Data.topFilesExt[3].value],
                [Data.topFilesExt[4].ident, Data.topFilesExt[4].value]
            ]
        }],
        colors: ['#354F5D', '#34B39E', '#E1584D', '#EEA19B', '#BCBCBC']
    });
}

/**
 * Create the chart for the
 * most line of code in folders
 */
function generateTopFoldersLocChart() {
    var max = 25;
    $('#topFoldersLoc').highcharts({
        chart: {
            type: 'bar'
        },
        title: {
            text: null
        },
        xAxis: {
            categories: [
                cut(Data.topFoldersLoc[0].ident, max),
                cut(Data.topFoldersLoc[1].ident, max),
                cut(Data.topFoldersLoc[2].ident, max),
                cut(Data.topFoldersLoc[3].ident, max),
                cut(Data.topFoldersLoc[4].ident, max)
            ],
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
            pointFormat: '{series.name}: <b>{point.y}</b>'
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
            data: [
                Data.topFoldersLoc[0].value,
                Data.topFoldersLoc[1].value,
                Data.topFoldersLoc[2].value,
                Data.topFoldersLoc[3].value,
                Data.topFoldersLoc[4].value
            ]
        }],
        colors: ['#3E4A50']
    });
}

/**
 * Create the chart for the
 * biggest files by size
 */
function generateTopFilesSizeChart () {
    $('#topFilesSize').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: null
        },
        xAxis: {
            categories: [
                Data.topFilesSize[0].ident,
                Data.topFilesSize[1].ident,
                Data.topFilesSize[2].ident,
                Data.topFilesSize[3].ident,
                Data.topFilesSize[4].ident
            ],
            labels: {
                enabled: false
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: null
            }
        },
        tooltip: {
            formatter: function() {
                return '<span style="font-size:10px">' + this.x + '</span><br>' + 
                        this.series.name + ': <b>' + size(this.point.y) + '</b>';
            }
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0,
                showInLegend: false
            }
        },
        credits: {
            enabled: false
        },
        series: [{
            name: 'Size',
            data: [
                Data.topFilesSize[0].value,
                Data.topFilesSize[1].value,
                Data.topFilesSize[2].value,
                Data.topFilesSize[3].value,
                Data.topFilesSize[4].value
            ]
        }],
        colors: ['#34B39E']
    });
}

/**
 * Create the chart for the
 * biggest files by lines of code
 */
function generateTopFilesLocChart () {
    $('#topFilesLoc').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: null
        },
        xAxis: {
            categories: [
                Data.topFilesLoc[0].ident,
                Data.topFilesLoc[1].ident,
                Data.topFilesLoc[2].ident,
                Data.topFilesLoc[3].ident,
                Data.topFilesLoc[4].ident
            ],
            labels: {
                enabled: false
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: null
            }
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y}</b>'
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0,
                showInLegend: false
            }
        },
        credits: {
            enabled: false
        },
        series: [{
            name: 'Lines of code',
            data: [
                Data.topFilesLoc[0].value,
                Data.topFilesLoc[1].value,
                Data.topFilesLoc[2].value,
                Data.topFilesLoc[3].value,
                Data.topFilesLoc[4].value
            ]
        }],
        colors: ['#E1584D']
    });
}

/**
 * Create the chart for the
 * allocation of lines of code by file types
 */
function generateTopExtLocChart() {
    $('#topExtLoc').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: null
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.y}</b> ({point.percentage:.1f}%)'
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
            name: 'Lines of code',
            data: [
                [Data.topExtLoc[0].ident, Data.topExtLoc[0].value],
                [Data.topExtLoc[1].ident, Data.topExtLoc[1].value],
                [Data.topExtLoc[2].ident, Data.topExtLoc[2].value],
                [Data.topExtLoc[3].ident, Data.topExtLoc[3].value],
                [Data.topExtLoc[4].ident, Data.topExtLoc[4].value]
            ]
        }],
        colors: ['#354F5D', '#34B39E', '#E1584D', '#EEA19B', '#BCBCBC']
    });
}