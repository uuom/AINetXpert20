$(function () {
    refresh(null);
});

function refresh(jsonStr){
    if(jsonStr==null){
        return;
    }
    var json = JSON.parse(jsonStr);
    $('#container').highcharts({
            chart: {
                 type: 'column'
            },
            title: {
                text: 'Highcharts'
            },
            xAxis: {
                categories: json.categories
            },
            yAxis:[{
                labels: {
                    format: '{value}￥'
                },
                title: {
                    text: '单价'
                }
                },{
                labels: {
                    format: '{value}%'
                },
                title: {
                    text: '比率'
                },
                opposite: true
            }],
            series: [{
                color: '#4572A7',
                type: 'spline',
                yAxis: 1,
                name: '单价',
                data: json.series[0].data,
                tooltip: {
                    valueSuffix: '￥'
                }
            }, {
                color: '#89A54E',
                type: 'spline',
                name: '比率',
                data: json.series[1].data,
                tooltip: {
                    valueSuffix: '%'
                }
            }],
            credits:{
                enabled:false
            }
        });
};