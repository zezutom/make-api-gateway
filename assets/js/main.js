let myTable;

function getAppDetail(appName) {
    return $.ajax({
        url: `/app/${appName}`,
        type: "GET",
        cache: true,
        dataType: 'json',
    });
}

function renderAppDetailContainer() {
    return $('<div id="app-detail" style="padding:5px 0" class="childWrap"></div>');
}

$(document).ready(function () {
    myTable = $('#appsOverview').DataTable({
        ajax: {
            url: "/apps",
            type: "GET",
            cache: true,
            dataType: 'json',
            dataSrc: 'result'
        },
        paging: true,
        sort: true,
        searching: true,
        columns: [
            {
                'className': 'details-control',
                'orderable': false,
                'data': null,
                'defaultContent': ''
            },
            {
                'className': 'details-control',
                'data': 'icon',
                render: function (data, type, row) {
                    return `<img src="${data}" alt="icon">`;
                }
            },
            {
                'className': 'details-control',
                'data': 'name',
                render: function (data, type, row) {
                    return `${data}`;
                }
            },
            {
                'className': 'details-control',
                'data': 'label',
                render: function (data, type, row) {
                    return `${data}`;
                }
            },
            {
                'className': 'details-control',
                'data': 'beta'
            },
            {
                'className': 'details-control',
                'data': 'public'
            },
        ]
    });
    $('#appsOverview tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = myTable.row(tr);

        if (tr.hasClass('shown')) {
            $('div.childWrap', row.child()).slideUp(function () {
                tr.removeClass('shown');
                row.child().remove();
            });

        } else {
            $.when(getAppDetail(row.data().name)).then(function (res) {
                row.child(renderAppDetailContainer(), 'no-padding').show();
                $('#app-detail').jsonViewer(res.result.app, {collapsed: true, rootCollapsable: false});
                tr.addClass('shown');
                $('div.childWrap', row.child()).slideDown();
            });
        }
    });
});