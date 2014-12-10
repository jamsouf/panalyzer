/**
 * Static generated data
 * from the analyzing
 */
var Data = {
    project: '/var/www/langr.io/',
    analyzed: 'Thu, 15 Dec 2014 14:05',
    folders: 32284,
    files: 958147,
    linesOfCode: 121852364
};

/**
 * Execute all
 * operations at
 * startup
 */
(function run() {
    write('dt.project', Data.project);
    write('dt.analyzed', Data.analyzed);
    write('dt.folders', nbr(Data.folders));
    write('dt.files', nbr(Data.files));
    write('dt.loc', nbr(Data.linesOfCode));
})();

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