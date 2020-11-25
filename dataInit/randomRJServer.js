"use strict";
const pool = require('./core/pool')
const moment = require('moment')

const currDay = moment().format('yyyy-MM-DD')
const key = 'rUsHrDlu'

// 营业部日人均数据
async function main1(){
    let sql = 'select a.value,a.grid_code,b.userNum from t_grid_statistics a left join (select a.parent_code,count(b.emp_code) as userNum from (select code,parent_code from t_grid_m where level = 5) a left join dwr_jxyz_emp_d b on a.code = b.grid_code where b.emp_code is not null GROUP BY a.parent_code) b on a.grid_code = b.parent_code where a.`key` = "T0Uk9Gf3" and a.level = 4 '
    const deptList = await pool.query(sql)
    deptList.forEach(element => {
      if (element.userNum) {
        const value = parseFloat(element.value/element.userNum).toFixed(2) 
        const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${element.grid_code}', '4', 'mainDownLeftCount', '${key}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
        console.log(sql1)
      }
    });
}

// 段道日人均数据
async function main2(){
  let sql = 'select a.value,a.grid_code,b.userNum from t_grid_statistics a left join (select a.code,count(b.emp_code) as userNum from t_grid_m a left join dwr_jxyz_emp_d b on a.code = b.grid_code where b.emp_code is not null and a.level = 5 GROUP BY a.code) b on a.grid_code = b.code where a.`key` = "T0Uk9Gf3" and a.level = 5'
  const deptList = await pool.query(sql)
  deptList.forEach(element => {
    if (element.userNum) {
      const value = parseFloat(element.value/element.userNum).toFixed(2) 
      const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${element.grid_code}', '5', 'mainDownLeftCount', '${key}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
      console.log(sql1)
    }
  });
}

// 区县日人均数据
async function main3(){
  let sql = 'select a.value,a.grid_code,b.userNum from t_grid_statistics a left join (select a.parent_code,count(b.emp_code) as userNum from (select a.code as code,b.parent_code as parent_code from t_grid_m a left join t_grid_m b on a.parent_code = b.code where a.level = 5) a left join dwr_jxyz_emp_d b on a.code = b.grid_code where b.emp_code is not null GROUP BY a.parent_code) b on a.grid_code = b.parent_code where a.`key` = "T0Uk9Gf3" and a.level = 3'
  const deptList = await pool.query(sql)
  deptList.forEach(element => {
    if (element.userNum) {
      const value = parseFloat(element.value/element.userNum).toFixed(2) 
      const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${element.grid_code}', '3', 'mainDownLeftCount', '${key}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
      console.log(sql1)
    }
  });
}

// 州市日人均数据
async function main4(){
  let sql = 'select a.value,a.grid_code,b.userNum from t_grid_statistics a left join (select a.parent_code,count(b.emp_code) as userNum from (select a.code as code,c.parent_code as parent_code from t_grid_m a left join t_grid_m b on a.parent_code = b.code left join t_grid_m c on b.parent_code = c.code where a.level = 5) a left join dwr_jxyz_emp_d b on a.code = b.grid_code where b.emp_code is not null GROUP BY a.parent_code) b on a.grid_code = b.parent_code where a.`key` = "T0Uk9Gf3" and a.level = 2'
  const deptList = await pool.query(sql)
  deptList.forEach(element => {
    if (element.userNum) {
      const value = parseFloat(element.value/element.userNum).toFixed(2) 
      const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${element.grid_code}', '2', 'mainDownLeftCount', '${key}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
      console.log(sql1)
    }
  });
}


// 省日人均数据
async function main5(){
  let sql = 'select a.value,a.grid_code from t_grid_statistics a where a.`key` = "T0Uk9Gf3" and a.level = 1'
  const data1 = await pool.query(sql)
  let sql1 = 'select count(*) as userNum from dwr_jxyz_emp_d where grid_code is not null'
  const data2 = await pool.query(sql1)
  const value = parseFloat(data1[0].value/data2[0].userNum).toFixed(2) 
  const element = data1[0]
  const sql2 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${element.grid_code}', '1', 'mainDownLeftCount', '${key}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
  console.log(sql2)
}
main1()
main2()
main3()
main4()
main5()


setTimeout(() =>{
  pool.end()
},60000)