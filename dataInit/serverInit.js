const moment = require('moment')
const normConfig = [
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '当月揽收收入(万元)',      
      code: 'PQdIJEvH',
      toFix: 2,
      value: 100000
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务量',  
      name: '当月揽收量(件)',
      code: 'h1c1Jjpd',
      toFix: 0,
      value: 100000
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务量',
      name: '本年累计揽收量(件)',
      code: 'SdKxDdF7',
      toFix: 0,
      value: 100000
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '当月环比增幅',
      code: 'eFKz3RDb',
      toFix: 2,
      text: '%',
      value: 100000
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '昨日揽收收入(万元)',
      code: 'DALngQA0',
      toFix: 2,
      value: 100000
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '昨日环比增幅',
      code: 'pcNXGE7K',
      toFix: 2,
      text: '%',
      value: 100000
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '本年累计揽收收入(万元)',
      code: 'bZLMLt5K',
      toFix: 2,
      value: 100000
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '本年环比增幅',
      code: 'gaNxlBlV',
      toFix: 2,
      text: '%',
      value: 100000
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务量',
      name: '日人均揽收量(件)',
      code: 'rUsHrDlu',
      toFix: 0,
      value: 100000
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务量',
      name: '昨日揽收量(件)',
      code: 'T0Uk9Gf3',
      toFix: 0,
      value: 100000
    },
    {
      groubName: '现费指标',
      classifyName: '现费指标',
      name: '昨日揽收量(件)',
      code: 'ptJaygoo',
      toFix: 0,
      value: 100000
    },
    {
      groubName: '现费指标',
      classifyName: '现费指标',
      name: '昨日揽收收入(万元)',
      code: 'Jej5IdxX',
      toFix: 2,
      value: 100000
    },
    {
      groubName: '现费指标',
      classifyName: '现费指标',
      name: '当月揽收量(件)',
      code: 'fiM5oVr1',
      toFix: 0,
      value: 100000
    },
    {
      groubName: '现费指标',
      classifyName: '现费指标',
      name: '当月揽收收入(万元)',
      code: 'cyP04YKV',
      toFix: 2,
      value: 100000
    }
  ]

const Day = parseInt(moment().format('DD'))-2
const currDay = moment().format('yyyy-MM-DD')
const lastMonth = moment().month(moment().month() - 1).startOf('month').format("YYYY-MM");
const curMonth = moment().add(0, 'month').format('YYYY-MM')

let dataList = []

async function main(){
    // 查询组织信息
    let sql = `select * from income;`
    
    global.pool.query(sql).then(res =>{
        dataList = res
        normConfig.forEach(item =>{
            const code = item.code
            switch(code){
                case 'PQdIJEvH': // 当月揽收收入
                    getCurrMonthTotal1(code)
                    break;
                case 'h1c1Jjpd': // 当月揽收量
                    getNum1(code)
                    break;
                case 'SdKxDdF7': // 本年累计揽收量
                    getNum2(code)
                    break;
                case 'bZLMLt5K': // 本年累计揽收收入
                    getCurrMonthTotal2(code)
                    break;
                case 'eFKz3RDb': // 当月环比增幅
                    getAmplification1(code)
                    break;
                case 'DALngQA0': // 昨日揽收收入
                    getCurrMonthTotal3(code)
                    break;
                case 'pcNXGE7K': // 昨日环比增幅
                    getAmplification2(code)
                    break;
                case 'gaNxlBlV': // 本年环比增幅
                    getAmplification3(code)
                    break;
                case 'ptJaygoo': // 昨日揽收量 - 现费指标
                    getNum5(code)
                    break;
                case 'Jej5IdxX': // 昨日揽收收入 - 现费指标
                getCurrMonthTotal4(code)
                    break;
                case 'fiM5oVr1': // 当月揽收量 - 现费指标
                    getNum4(code)
                    break;
                case 'cyP04YKV': // 当月揽收收入 - 现费指标
                    getCurrMonthTotal5(code)
                    break;
                case 'T0Uk9Gf3': // 昨日揽收量
                    getNum6(code)
                    break;
            }
        })
    })
    return false
}

// 当月揽收收入
function getCurrMonthTotal1(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${parseFloat(item.last_month_postage_total/10000).toFixed(2)}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}

// 本年累计揽收收入
function getCurrMonthTotal2(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${parseFloat(item.year_postage_total/10000).toFixed(2)}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}
// 昨日揽收收入
function getCurrMonthTotal3(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${parseFloat(item.cur_day_total/10000).toFixed(2)}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}
// 昨日揽收收入 - 现费指标
function getCurrMonthTotal4(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${parseFloat(item.cur_day_total1_s/10000).toFixed(2)}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}
// 当月揽收收入 - 现费指标
function getCurrMonthTotal5(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${parseFloat(item.last_postage_total/10000).toFixed(2)}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}

// 当月现费收入
function getCurrMonthTotal6(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${parseFloat(item.last_postage_total/10000).toFixed(2)}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}

// 当月揽收量
function getNum1(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    const lastArr = dataList.filter(item => item.period_id === lastMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${item.last_month_clledted_qty}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}

// 本年累计揽收量
function getNum2(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    const lastArr = dataList.filter(item => item.period_id === lastMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${item.year_collected_qty}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}

// 日人均揽收量
// function getNum3(code){
//     const currArr = dataList.filter(item => item.period_id === curMonth)
//     const lastArr = dataList.filter(item => item.period_id === lastMonth)
//     currArr.forEach(item =>{
//         const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${item.last_month_clledted_qty}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
//          global.execSql(sql1)
//     })
// }

 // 当月揽收量 - 现费指标
function getNum4(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    const lastArr = dataList.filter(item => item.period_id === lastMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${item.last_collected_qty}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}

// 昨日揽收量 - 现费指标
function getNum5(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${item.cur_day_qty_s}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}

 // 昨日揽收量
function getNum6(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    const lastArr = dataList.filter(item => item.period_id === lastMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${item.cur_day_qty}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}

// 当月环比增幅
function getAmplification1(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    const lastArr = dataList.filter(item => item.period_id === lastMonth)
    currArr.forEach(item =>{
        const currValue = item.last_month_postage_total
        const emp = lastArr.find(dd => dd.dept_code === item.dept_code)
        const last = emp ? emp.last_month_postage_total : 0
        const lastValue = last/31*Day
        const value = parseInt(lastValue) != 0 ? (Math.round((parseFloat(currValue) - parseFloat(lastValue)) / parseFloat(lastValue) * 10000) / 100.00)+"%" : '0.00%'
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}

// 昨日环比增幅
function getAmplification2(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    currArr.forEach(item =>{
        const currValue = item.cur_day_total
        const lastValue = item.last_day_total
        const value = parseInt(lastValue) != 0 ? (Math.round((parseFloat(currValue) - parseFloat(lastValue)) / parseFloat(lastValue) * 10000) / 100.00)+"%" : '0.00%'
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}

// 当月环比增幅
function getAmplification3(code){
    const currArr = dataList.filter(item => item.period_id === curMonth)
    currArr.forEach(item =>{
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 4, 'mainDownLeftCount', '${code}', 0, NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
         global.execSql(sql1)
    })
}



module.exports = {
    init: () => {
      return new Promise(function (resolve, reject) {
        main()
        setTimeout(() =>{
          resolve()
        },180000)
      })
    }
  };
  