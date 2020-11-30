const Excel = require('exceljs')
const fs = require('fs')
const moment = require('moment')
function getCellValue(cell) {
  let value = cell.value
  const type = cell.type
  if (value) {
    if (type === 6) { // 公式类型
      value = value.result
    } else if (type === 8) { // 富文本类型
      value = cell.value.richText.map(item => item.text).join('')
    } else if (value instanceof Date) {
      value = dataUtil.formatDate(
        value,
        'yyyy-MM-dd'
      )
    } else if (value instanceof Object) {
      value = value.text
    }
  }
  return value
}

const lastMonth = moment().month(moment().month() - 1).startOf('month').format("YYYY-MM");
const curMonth = moment().add(0, 'month').format('YYYY-MM')
const sqlList = []
function parseExcelData() {
  var workbook = new Excel.Workbook()
    workbook.xlsx.readFile('./excel/标准上月.xlsx')
      .then(function() {
        const worksheet = workbook.getWorksheet('机构量收分析表(个计)')
        worksheet.eachRow(function(row, rowNumber) {
          const item = {}
          if (rowNumber > 4 & rowNumber < worksheet.rowCount) {
            item['dept_code'] = getCellValue(row.getCell(5))
            item['last_month_clledted_qty'] = getCellValue(row.getCell(10))
            item['last_month_postage_total'] = getCellValue(row.getCell(11))
            item['year_collected_qty'] = getCellValue(row.getCell(13))
            item['year_postage_total'] = getCellValue(row.getCell(14))
            if (item['dept_code']) {
              const sql = `replace INTO income(dept_code, period_id, cur_day_qty, cur_day_total, cur_day_qty_s, cur_day_total1_s, last_day_qty, last_day_total, last_day_qty_s, last_day_total1_s, last_month_clledted_qty, last_month_postage_total, last_collected_qty, last_postage_total, year_collected_qty, year_postage_total, modify_date) VALUES ( '${item.dept_code}', '${lastMonth}', ${item.cur_day_qty}, ${item.cur_day_total}, ${item.cur_day_qty_s}, ${item.cur_day_total1_s}, ${item.last_day_qty}, ${item.last_day_total}, ${item.last_day_qty_s}, ${item.last_day_total1_s}, ${item.last_month_clledted_qty}, ${item.last_month_postage_total}, ${item.last_collected_qty}, ${item.last_postage_total}, ${item.year_collected_qty}, ${item.year_postage_total}, now());`
              
              global.pool.query(sql.replace(/undefined/g,0))
            }
          }
        })
      })
}

function parseExcelData11() {
  var workbook = new Excel.Workbook()
    workbook.xlsx.readFile('./excel/标准昨日.xlsx')
      .then(function() {
        const worksheet = workbook.getWorksheet('机构量收分析表(个计)')
        worksheet.eachRow(function(row, rowNumber) {
          const item = {}
          if (rowNumber > 4 & rowNumber < worksheet.rowCount) {
            item['dept_code'] = getCellValue(row.getCell(5))
            item['cur_day_qty'] = getCellValue(row.getCell(7))
            item['cur_day_total'] = getCellValue(row.getCell(8))
            item['last_month_clledted_qty'] = getCellValue(row.getCell(10))
            item['last_month_postage_total'] = getCellValue(row.getCell(11))
            item['year_collected_qty'] = getCellValue(row.getCell(13))
            item['year_postage_total'] = getCellValue(row.getCell(14))
            if (item['dept_code']) {
              const sql = `replace INTO income(dept_code, period_id, cur_day_qty, cur_day_total, cur_day_qty_s, cur_day_total1_s, last_day_qty, last_day_total, last_day_qty_s, last_day_total1_s, last_month_clledted_qty, last_month_postage_total, last_collected_qty, last_postage_total, year_collected_qty, year_postage_total, modify_date) VALUES ( '${item.dept_code}', '${curMonth}', ${item.cur_day_qty}, ${item.cur_day_total}, ${item.cur_day_qty_s}, ${item.cur_day_total1_s}, ${item.last_day_qty}, ${item.last_day_total}, ${item.last_day_qty_s}, ${item.last_day_total1_s}, ${item.last_month_clledted_qty}, ${item.last_month_postage_total}, ${item.last_collected_qty}, ${item.last_postage_total}, ${item.year_collected_qty}, ${item.year_postage_total}, now());`
              // console.log(sql.replace(/undefined/g,0))
              global.pool.query(sql.replace(/undefined/g,0))
            }
          }
        })
      })
}

function parseExcelData12() {
  var workbook = new Excel.Workbook()
    workbook.xlsx.readFile('./excel/标准前日.xlsx')
      .then(function() {
        const worksheet = workbook.getWorksheet('机构量收分析表(个计)')
        worksheet.eachRow(function(row, rowNumber) {
          const item = {}
          if (rowNumber > 4 & rowNumber < worksheet.rowCount) {
            item['dept_code'] = getCellValue(row.getCell(5))
            item['last_day_qty'] = getCellValue(row.getCell(7))
            item['last_day_total'] = getCellValue(row.getCell(8))
            if (item['dept_code']) {
              const sql = `update income set last_day_qty = ${item.last_day_qty},last_day_total = ${item.last_day_total} where period_id = '${curMonth}' and dept_code = ${item.dept_code};`
              global.pool.query(sql)
            }
          }
        })
      })
}


function parseExcelData13() {
  var workbook = new Excel.Workbook()
    workbook.xlsx.readFile('./excel/散户昨日.xlsx')
      .then(function() {
        const worksheet = workbook.getWorksheet('机构量收分析表(个计)')
        worksheet.eachRow(function(row, rowNumber) {
          const item = {}
          if (rowNumber > 4 & rowNumber < worksheet.rowCount) {
            item['dept_code'] = getCellValue(row.getCell(5))
            item['cur_day_qty_s'] = getCellValue(row.getCell(7))
            item['cur_day_total1_s'] = getCellValue(row.getCell(8))
            item['last_collected_qty'] = getCellValue(row.getCell(10))
            item['last_postage_total'] = getCellValue(row.getCell(11))
            if (item['dept_code']) {
              const sql = `update income set cur_day_qty_s = ${item.cur_day_qty_s},cur_day_total1_s = ${item.cur_day_total1_s},last_collected_qty = ${item.last_collected_qty},last_postage_total = ${item.last_postage_total}  where period_id = '${curMonth}' and dept_code = ${item.dept_code};`
              // console.log(sql)
              global.pool.query(sql)
            }
          }
        })
      })
}



function parseExcelData14() {
  var workbook = new Excel.Workbook()
    workbook.xlsx.readFile('./excel/散户前日.xlsx')
      .then(function() {
        const worksheet = workbook.getWorksheet('机构量收分析表(个计)')
        worksheet.eachRow(function(row, rowNumber) {
          const item = {}
          if (rowNumber > 4 & rowNumber < worksheet.rowCount) {
            item['dept_code'] = getCellValue(row.getCell(5))
            item['last_day_qty_s'] = getCellValue(row.getCell(7))
            item['last_day_total1_s'] = getCellValue(row.getCell(8))
            if (item['dept_code']) {
              const sql = `update income set last_day_qty_s = ${item.last_day_qty_s},last_day_total1_s = ${item.last_day_total1_s} where period_id = '${curMonth}' and dept_code = ${item.dept_code};`
              // console.log(sql)
              global.pool.query(sql)
            }
          }
        })
      })
}


function initIncomeList(){
  let sql = 'select dept_code,period_id,cur_day_total,last_day_total,last_month_postage_total from income'
  global.pool.query(sql).then(res =>{
    res.forEach(element => {
      global.pool.query(`select a.code,a.name,a.level,a.parent_code as parentCode from t_grid_m a LEFT join (select grid_code from dwr_jxyz_emp_d GROUP BY grid_code) b on a.code = b.grid_code where parent_code = '${element.dept_code}'`).then(deptList =>{
          if (deptList.length == 1){
            const item = deptList[0]
            const sql = `insert income_list (dept_code,period_id,cur_day_total,last_day_total,last_month_postage_total) values('${item.code}','${element.period_id}','${element.cur_day_total}','${element.last_day_total}','${element.last_month_postage_total}');`
            global.pool.query(sql)
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
              // console.log(sql1)
              global.pool.query(sql1)
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
            // console.log(sql1)
            global.pool.query(sql1)
          }
        }
      })  
    });
  })
}

module.exports = {
  parseExecl: (sql, values) => {
    return new Promise(function (resolve, reject) {
      setTimeout(() =>{
        parseExcelData()
        parseExcelData11()
      },0)
      
      setTimeout(() =>{
        parseExcelData12()
        parseExcelData13()
        parseExcelData14()
      },8000)
      setTimeout(() =>{
        initIncomeList()
      },12000)
      setTimeout(() =>{
        resolve()
      },40000)
    })
  }
};
