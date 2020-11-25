const pool = require('./core/pool')
const moment = require('moment')
const arr1 = [
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
    // {
    //   groubName: '作战图',
    //   classifyName: '特快专递业务量',
    //   name: '日人均揽收量(件)',
    //   code: 'rUsHrDlu',
    //   toFix: 0,
    //   value: 100000
    // },
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
      name: '当月揽收量(件)',
      code: 'fiM5oVr1',
      toFix: 0,
      value: 100000
    }
  ]
  const arr3 = [
    {
        groubName: '现费指标',
        classifyName: '现费指标',
        name: '当月揽收收入(万元)',
        code: 'cyP04YKV',
        toFix: 2,
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
        groubName: '作战图',
        classifyName: '特快专递业务收入',
        name: '当月现费收入(万元)',
        code: 'o2gzp0bK',
        toFix: 2,
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
        name: '昨日揽收收入(万元)',
        code: 'DALngQA0',
        toFix: 2,
        value: 100000
      },
      {
        groubName: '作战图',
        classifyName: '特快专递业务收入',
        name: '当月揽收收入(万元)',      
        code: 'PQdIJEvH',
        toFix: 2,
        value: 100000
      }
  ]
  const arr2 = [
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
        name: '本年环比增幅',
        code: 'gaNxlBlV',
        toFix: 2,
        text: '%',
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
      }
  ]
const currMonth = moment().format('yyyy-MM')
const currDay = moment().format('yyyy-MM-DD')
const currYear = moment().format('yyyy')

let dataList = []

function groupArr(list,field){
    var fieldList = [],att=[];
    list.map((e)=>{
        fieldList.push(e[field])
    })
    //数组去重
    fieldList = fieldList.filter((e,i,self)=>{
        return self.indexOf(e)==i
    })
    for(var j=0;j<fieldList.length;j++){
        //过滤出匹配到的数据
        var arr = list.filter((e)=>{
            return e.parent_code==fieldList[j];
        })
        att.push({
            parent_code:arr[0].parent_code,
            list:arr
        })
    }
    return att;
}

// 件
async function main(code,level){
    // 查询组织信息
    const levels = level+1
    let sql = `select a.grid_code,a.value,a.key,b.parent_code  from t_grid_statistics a left join t_grid_m b on b.code = a.grid_code where a.key = '${code}' and a.level = ${levels}`
    pool.query(sql).then(deptList =>{
        const level3Group = groupArr(deptList.filter(item => item.parent_code != null),'parent_code')
        level3Group.forEach(item =>{
            const totalNum = item.list.reduce(function (total, currentValue, currentIndex, arr) {
                return parseInt(total) + parseInt(currentValue.value);
            }, 0);
            const sql = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.parent_code}', '${level}', 'mainDownLeftCount', '${code}', '${totalNum}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
            console.log(sql)
        })
    })
    return false
}

// 收入
async function main1(code,level){
    // 查询组织信息
    const levels = level+1
    let sql = `select a.grid_code,a.value,a.key,b.parent_code  from t_grid_statistics a left join t_grid_m b on b.code = a.grid_code where a.key = '${code}' and a.level = ${levels}`
    pool.query(sql).then(deptList =>{
        const level3Group = groupArr(deptList.filter(item => item.parent_code != null),'parent_code')
        level3Group.forEach(item =>{
            const totalNum = item.list.reduce(function (total, currentValue, currentIndex, arr) {
                return parseFloat(total) + parseFloat(currentValue.value);
            }, 0);
            const sql = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.parent_code}', '${level}', 'mainDownLeftCount', '${code}', '${totalNum.toFixed(2)}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
            console.log(sql)
        })
    })
    return false
}


// 昨日环比增幅
async function main2(code,level){
    let sql = `select sum(a.cur_day_total) as currDay,sum(a.last_day_total)  as lastDay,b.parent_code from income a LEFT join t_grid_m b on a.dept_code = b.code where a.period_id = '2020-11' GROUP BY b.parent_code`
    pool.query(sql).then(deptList =>{
        deptList.forEach(item =>{
            const value = parseInt(item.lastDay) != 0 ? (Math.round((parseFloat(item.currDay) - parseFloat(item.lastDay)) / parseFloat(item.lastDay) * 10000) / 100.00)+"%" : '0.00%'
            const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.parent_code}', ${level}, 'mainDownLeftCount', '${code}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
            console.log(sql1)
        })
    })
    return false
}

// 当月环比增幅
async function main3(code,level){
    let sql = `select a.last_month_postage_total as currMonth,c.last_month_postage_total as lastMonth,b.parent_code,b.code from income a LEFT join (select dept_code,last_month_postage_total from income where period_id = '2020-10') c on a.dept_code = c.dept_code LEFT join t_grid_m b on a.dept_code = b.code and period_id = '2020-11'`
    pool.query(sql).then(deptList =>{
        const level3Group = groupArr(deptList.filter(item => item.parent_code != null),'parent_code')
        level3Group.forEach(item =>{
            const totalCurNum = item.list.reduce(function (total, currentValue, currentIndex, arr) {
                return parseFloat(total) + parseFloat(currentValue.currMonth);
            }, 0);
            const totalLastNum = item.list.reduce(function (total, currentValue, currentIndex, arr) {
                return parseFloat(total) + parseFloat(currentValue.lastMonth);
            }, 0);
            const value = parseInt(totalLastNum) != 0 ? (Math.round((parseFloat(totalCurNum) - parseFloat(totalLastNum)) / parseFloat(totalLastNum) * 10000) / 100.00)+"%" : '0.00%'
            const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.parent_code}', ${level}, 'mainDownLeftCount', '${code}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
            console.log(sql1)
        })
    })
    return false
}

// 当月环比增幅 -- 市级
async function main4(code,level){
    let sql = `select a.last_month_postage_total as currMonth,c.last_month_postage_total as lastMonth,b.parent_code,b.code from income a LEFT join (select dept_code,last_month_postage_total from income where period_id = '2020-10') c on a.dept_code = c.dept_code LEFT join (select a.code as code,b.parent_code as parent_code from t_grid_m a left join t_grid_m b on a.parent_code = b.code where a.level = 4) b on a.dept_code = b.code where period_id = '2020-11'`
    pool.query(sql).then(deptList =>{
        const level3Group = groupArr(deptList.filter(item => item.parent_code != null),'parent_code')
        level3Group.forEach(item =>{
            const totalCurNum = item.list.reduce(function (total, currentValue, currentIndex, arr) {
                return parseFloat(total) + parseFloat(currentValue.currMonth);
            }, 0);
            const totalLastNum = item.list.reduce(function (total, currentValue, currentIndex, arr) {
                return parseFloat(total) + parseFloat(currentValue.lastMonth);
            }, 0);
            const value = parseInt(totalLastNum) != 0 ? (Math.round((parseFloat(totalCurNum) - parseFloat(totalLastNum)) / parseFloat(totalLastNum) * 10000) / 100.00)+"%" : '0.00%'
            const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.parent_code}', ${level}, 'mainDownLeftCount', '${code}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
            console.log(sql1)
        })
    })
    return false
}


// 当日环比增幅 -- 市级
async function main5(code,level){
    let sql = `select sum(a.cur_day_total) as currDay,sum(a.last_day_total)  as lastDay,b.parent_code from income a LEFT join (select a.code as code,b.parent_code as parent_code from t_grid_m a left join t_grid_m b on a.parent_code = b.code where a.level = 4) b on a.dept_code = b.code where a.period_id = '2020-11' GROUP BY b.parent_code`
    pool.query(sql).then(deptList =>{
        deptList.forEach(item =>{
            const value = parseInt(item.lastDay) != 0 ? (Math.round((parseFloat(item.currDay) - parseFloat(item.lastDay)) / parseFloat(item.lastDay) * 10000) / 100.00)+"%" : '0.00%'
            const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.parent_code}', ${level}, 'mainDownLeftCount', '${code}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
            console.log(sql1)
        })
    })
    return false
}


// 当月环比增幅 -- 省级
async function main6(code,level){
    let sql = `select a.last_month_postage_total as currMonth,c.last_month_postage_total as lastMonth,b.parent_code,b.code from income a LEFT join (select dept_code,last_month_postage_total from income where period_id = '2020-10') c on a.dept_code = c.dept_code LEFT join (select a.code as code,c.parent_code as parent_code from t_grid_m a left join t_grid_m b on a.parent_code = b.code left join t_grid_m c on b.parent_code = c.code where a.level = 4) b on a.dept_code = b.code where period_id = '2020-11' `
    pool.query(sql).then(deptList =>{
        const level3Group = groupArr(deptList.filter(item => item.parent_code != null),'parent_code')
        level3Group.forEach(item =>{
            const totalCurNum = item.list.reduce(function (total, currentValue, currentIndex, arr) {
                return parseFloat(total) + parseFloat(currentValue.currMonth);
            }, 0);
            const totalLastNum = item.list.reduce(function (total, currentValue, currentIndex, arr) {
                return parseFloat(total) + parseFloat(currentValue.lastMonth);
            }, 0);
            const value = parseInt(totalLastNum) != 0 ? (Math.round((parseFloat(totalCurNum) - parseFloat(totalLastNum)) / parseFloat(totalLastNum) * 10000) / 100.00)+"%" : '0.00%'
            const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.parent_code}', ${level}, 'mainDownLeftCount', '${code}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
            console.log(sql1)
        })
    })
    return false
}

// 当日环比增幅 -- 省级
async function main7(code,level){
    let sql = `select sum(a.cur_day_total) as currDay,sum(a.last_day_total)  as lastDay,b.parent_code from income a LEFT join (select a.code as code,c.parent_code as parent_code from t_grid_m a left join t_grid_m b on a.parent_code = b.code left join t_grid_m c on b.parent_code = c.code where a.level = 4) b on a.dept_code = b.code where a.period_id = '2020-11' GROUP BY b.parent_code`
    pool.query(sql).then(deptList =>{
        deptList.forEach(item =>{
            const value = parseInt(item.lastDay) != 0 ? (Math.round((parseFloat(item.currDay) - parseFloat(item.lastDay)) / parseFloat(item.lastDay) * 10000) / 100.00)+"%" : '0.00%'
            const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.parent_code}', ${level}, 'mainDownLeftCount', '${code}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
            console.log(sql1)
        })
    })
    return false
}

// 计算区县数据
function level3Data(){
    arr1.forEach(item =>{
        main(item.code, 3)
    })
    arr3.forEach(item =>{
        main1(item.code, 3)
    })
    // 昨日环比增幅
    main2('pcNXGE7K', 3)
    // 当月环比增幅
    main3('eFKz3RDb', 3)
}

// 计算市级数据
function level2Data(){
    arr1.forEach(item =>{
        main(item.code, 2)
    })
    arr3.forEach(item =>{
        main1(item.code, 2)
    })
    //当月环比增幅 -- 市级
    main4('eFKz3RDb', 2)

    // 昨日环比增幅 -- 市级
    main5('pcNXGE7K', 2)
}

// 计算省数据
function level1Data(){
    arr1.forEach(item =>{
        main(item.code, 1)
    })
    arr3.forEach(item =>{
        main1(item.code, 1)
    })
    //当月环比增幅 -- 省级
    main6('eFKz3RDb', 1)

    // 昨日环比增幅 -- 省级
    main7('pcNXGE7K', 1)
}
// 先执行3级任务
// level3Data()

// 后执行2级任务
// level2Data()

// 后执行1级任务
// level1Data()

setTimeout(() =>{
    pool.end()
},10000)
