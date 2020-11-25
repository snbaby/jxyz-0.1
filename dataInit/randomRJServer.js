"use strict";
const pool = require('./core/pool')
const moment = require('moment')

const currDay = moment().format('yyyy-MM-DD')
const key = 'rUsHrDlu'
async function main(){
    // 查询组织信息
    let sql = `select code,name,level from t_grid_m`
    const deptList = await pool.query(sql)
    deptList.forEach(element => {
      const value = parseFloat((80+Math.random()*45)/10).toFixed(2) 
      const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${element.code}', '${element.level}', 'mainDownLeftCount', '${key}', '${value}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW());`;
      console.log(sql1)
    });
}
main()

setTimeout(() =>{
  pool.end()
},10000)