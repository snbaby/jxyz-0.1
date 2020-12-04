const pool = require('./core/pool')
const moment = require('moment')

const currDay = moment().format('yyyy-MM-DD')
/*
将imcome表数据随机分解道各个段道
*/

let sql = 'select dept_code,period_id,cur_day_total,last_day_total,last_month_postage_total from income'
  pool.query(sql).then(res =>{
    res.forEach(element => {
      pool.query(`select a.code,a.name,a.level,a.parent_code as parentCode from t_grid_m a LEFT join (select grid_code from dwr_jxyz_emp_d GROUP BY grid_code) b on a.code = b.grid_code where parent_code = '${element.dept_code}'`).then(deptList =>{
          if (deptList.length == 1){
            const item = deptList[0]
            const sql = `insert income_list (dept_code,period_id,cur_day_total,last_day_total,last_month_postage_total) values('${item.code}','${element.period_id}','${element.cur_day_total}','${element.last_day_total}','${element.last_month_postage_total}');`
            console.log(sql)
          } else {
            const total1 = element.cur_day_total
            const baseNum1 = total1/deptList.length // 获取基数
            const offset1 = (baseNum1/10).toFixed(0) // 获取基数
          
            const total2 = element.last_day_total
            const baseNum2 = total2/deptList.length // 获取基数
            const offset2 = (baseNum2/10).toFixed(0) // 获取基数

            const total3 = element.last_month_postage_total
            const baseNum3 = total3/deptList.length // 获取基数
            const offset3 = (baseNum3/10).toFixed(0) // 获取基数
            const first = deptList.pop()

            for(let index in deptList){
              const item = deptList[index]
              const randowNum1 = Math.random()*offset1
              const randowNum2 = Math.random()*offset2
              const randowNum3 = Math.random()*offset3
              if(index % 2 == 1){
                item.value1 = (parseFloat(randowNum1) + parseFloat(baseNum1)).toFixed(2)
                item.value2 = (parseFloat(randowNum2) + parseFloat(baseNum2)).toFixed(2)
                item.value3 = (parseFloat(randowNum3) + parseFloat(baseNum3)).toFixed(2)
              } else {
                item.value1 = (parseFloat(baseNum1) - parseFloat(randowNum1)).toFixed(2)
                item.value2 = (parseFloat(-randowNum2) + parseFloat(baseNum2)).toFixed(2)
                item.value3 = (parseFloat(-randowNum3) + parseFloat(baseNum3)).toFixed(2)
              }
              const sql1 = `insert income_list (dept_code,period_id,cur_day_total,last_day_total,last_month_postage_total) values('${item.code}','${element.period_id}','${item.value1}','${item.value2}','${item.value3}');`
              console.log(sql1)
          }

          if(first){
            // 已分配数据计算
            const totalNum1 = deptList.reduce(function (total, currentValue, currentIndex, arr) {
              return parseFloat(total) + parseFloat(currentValue.value1);
            }, 0);
            const totalNum2 = deptList.reduce(function (total, currentValue, currentIndex, arr) {
              return parseFloat(total) + parseFloat(currentValue.value2);
            }, 0);
            const totalNum3 = deptList.reduce(function (total, currentValue, currentIndex, arr) {
              return parseFloat(total) + parseFloat(currentValue.value3);
            }, 0);
            first.value1 = parseFloat(total1-totalNum1).toFixed(2)
            first.value2 = parseFloat(total2-totalNum2).toFixed(2)
            first.value3 = parseFloat(total3-totalNum3).toFixed(2)
            const sql1 = `insert income_list (dept_code,period_id,cur_day_total,last_day_total,last_month_postage_total) values('${first.code}','${element.period_id}','${first.value1}','${first.value2}','${first.value3}');`
            console.log(sql1)
          }
        }
      })  
    });
  })

  setTimeout(() =>{
    pool.end()
  },20000)