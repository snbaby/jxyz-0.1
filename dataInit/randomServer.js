"use strict";
const moment = require('moment')
const Day = parseInt(moment().format('DD'))-1 <= 0 ? 1 : parseInt(moment().format('DD'))-1
const currDay = moment().format('yyyy-MM-DD')
const lastMonth = moment().month(moment().month() - 1).startOf('month').format("YYYY-MM");
const curMonth = moment().add(0, 'month').format('YYYY-MM')
async function main(deptCode,total,key,toFix,text=''){
    total = parseFloat(total)
    // 查询组织信息
    let sql = `select a.code,a.name,a.level,a.parent_code as parentCode from t_grid_m a LEFT join (select grid_code from dwr_jxyz_emp_d GROUP BY grid_code) b on a.code = b.grid_code where b.grid_code is not null and parent_code = '${deptCode}'`
    const deptList = await global.execSql(sql)
    const deptJsonList = deptList.map(item =>{
      return {
          code:item.code,
          name:item.name,
          level:item.level,
          parentCode:item.parentCode
      }
  })
  const deptTree = deptJsonList.filter(item =>item.level == 5)
  await insertData(deptTree,total,key,toFix,text)
  return false
}

async function insertData(list,total,key,toFix,text){
    if (list) {
        if (list.length == 1) {
            const item = list[0]
            const value = Math.round(total * toFix) / toFix 
            const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.code}', '${item.level}', 'mainDownLeftCount', '${key}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
            global.execSql(sql1)
            if (list[0].children){
                return insertData(list[0].children,total,key,toFix,text)
            } else {
                return false
            }
        } else {
            const baseNum = total/list.length // 获取基数
            const offset = baseNum/10 // 获取基数
            const first = list.pop()
            for(let index in list){
                const item = list[index]
                const randowNum = Math.random()*offset
                if(index % 2 == 1){
                    item.value = Math.round((parseFloat(randowNum) + parseFloat(baseNum)) * toFix) / toFix 
                } else {
                    item.value = Math.round((parseFloat(baseNum)-parseFloat(randowNum)) * toFix) / toFix 
                }
                const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.code}', '${item.level}', 'mainDownLeftCount', '${key}', '${item.value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
                global.execSql(sql1)
                if (item.children){
                    insertData(item.children,item.value,key,toFix,text)
                }
            }
            // 已分配数据计算
            const totalNum = list.reduce(function (total, currentValue, currentIndex, arr) {
                return parseFloat(total) + parseFloat(currentValue.value);
            }, 0);
            if(first){
              first.value = Math.round(parseFloat(total-totalNum) * toFix) / toFix 
              const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${first.code}', '${first.level}', 'mainDownLeftCount', '${key}', '${first.value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
               global.execSql(sql1)
              if (first.children){
                  return insertData(first.children,first.value,key,toFix,text)
              } else {
                  return false
              }
            }
        }
    }
}

async function main1(code){
  let sql = `select dept_code,cur_day_total as currDay,last_day_total as lastDay,last_month_postage_total from income_list where period_id = '${curMonth}'`
  global.pool.query(sql).then(dataList =>{
      dataList.forEach(item =>{
        const value = parseInt(item.lastDay) != 0 ? (Math.round((parseFloat(item.currDay) - parseFloat(item.lastDay)) / parseFloat(item.lastDay) * 10000) / 100.00)+"%" : '0.00%'
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 5, 'mainDownLeftCount', '${code}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
        global.execSql(sql1)
      })
  })
  return false
}

async function main2(code){
  // 查询组织信息
  let sql = `select a.last_month_postage_total as currMonth,c.last_month_postage_total as lastMonth,a.dept_code from income_list a LEFT join (select dept_code,last_month_postage_total from income_list where period_id = '${lastMonth}') c on a.dept_code = c.dept_code where period_id = '${curMonth}'`
  global.pool.query(sql).then(dataList =>{
      dataList.forEach(item =>{
        const lastValue = item.lastMonth/31*Day
        const value = parseInt(lastValue) != 0 ? (Math.round((parseFloat(item.currMonth) - parseFloat(lastValue)) / parseFloat(lastValue) * 10000) / 100.00)+"%" : '0.00%'
        const sql1 = `replace INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${item.dept_code}', 5, 'mainDownLeftCount', '${code}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
        global.execSql(sql1)
      })
  })
  return false
}


function getData(){
  let sql = 'select a.`key`,a.`value`,a.grid_code,b.name from t_grid_statistics a LEFT JOIN t_layer_m b on b.code = a.key where level = 4 and `key` != "rUsHrDlu"'
  global.pool.query(sql).then(res =>{
    const arr1 = res.filter(item => item.name.indexOf('收入') > -1)
    const arr3 = res.filter(item => item.name.indexOf('量') > -1)
    const list1 = arr1.map(item =>{
      return {
        code: item.grid_code,
        key: item.key,
        toFix: 100,
        value: item.value
      }
    })

    const list3 = arr3.map(item =>{
      return {
        code: item.grid_code,
        key: item.key,
        toFix: 1,
        value: item.value
      }
    })
    const list = list1.concat(list3)
    list.forEach(item =>{
      main(item.code,item.value,item.key,item.toFix,item.text)
    })
    main1('pcNXGE7K')// 昨日环比增幅
    main2('eFKz3RDb') // 当月环比增幅
  })
}


module.exports = {
  randomData: (sql, values) => {
    return new Promise(function (resolve, reject) {
      getData()
      setTimeout(() =>{
        resolve()
      },300000)
    })
  }
};
