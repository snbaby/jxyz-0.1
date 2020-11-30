const Excel = require('exceljs')
const fs = require('fs')
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
const curMonth = '2020-11'
const lastMonth = '2020-10'
function parseExcelData() {
  var workbook = new Excel.Workbook()
    workbook.xlsx.readFile('./标准2020年10月.xlsx')
      .then(function() {
        const worksheet = workbook.getWorksheet('机构量收分析表(个计)')
        worksheet.eachRow(function(row, rowNumber) {
          const item = {}
          if (rowNumber > 4 & rowNumber < 385) {
            item['dept_code'] = getCellValue(row.getCell(5))
            item['last_month_clledted_qty'] = getCellValue(row.getCell(10))
            item['last_month_postage_total'] = getCellValue(row.getCell(11))
            item['year_collected_qty'] = getCellValue(row.getCell(13))
            item['year_postage_total'] = getCellValue(row.getCell(14))
            if (item['dept_code']) {
              const sql = `INSERT INTO income(dept_code, period_id, cur_day_qty, cur_day_total, cur_day_qty_s, cur_day_total1_s, last_day_qty, last_day_total, last_day_qty_s, last_day_total1_s, last_month_clledted_qty, last_month_postage_total, last_collected_qty, last_postage_total, year_collected_qty, year_postage_total, modify_date) VALUES ( '${item.dept_code}', '${lastMonth}', ${item.cur_day_qty}, ${item.cur_day_total}, ${item.cur_day_qty_s}, ${item.cur_day_total1_s}, ${item.last_day_qty}, ${item.last_day_total}, ${item.last_day_qty_s}, ${item.last_day_total1_s}, ${item.last_month_clledted_qty}, ${item.last_month_postage_total}, ${item.last_collected_qty}, ${item.last_postage_total}, ${item.year_collected_qty}, ${item.year_postage_total}, now());`
              console.log(sql.replace(/undefined/g,0))
            }
          }
        })
      })
}

function parseExcelData11() {
  var workbook = new Excel.Workbook()
    workbook.xlsx.readFile('./标准20201129.xlsx')
      .then(function() {
        const worksheet = workbook.getWorksheet('机构量收分析表(个计)')
        worksheet.eachRow(function(row, rowNumber) {
          const item = {}
          if (rowNumber > 4 & rowNumber < 232) {
            item['dept_code'] = getCellValue(row.getCell(5))
            item['cur_day_qty'] = getCellValue(row.getCell(7))
            item['cur_day_total'] = getCellValue(row.getCell(8))
            item['last_month_clledted_qty'] = getCellValue(row.getCell(10))
            item['last_month_postage_total'] = getCellValue(row.getCell(11))
            item['year_collected_qty'] = getCellValue(row.getCell(13))
            item['year_postage_total'] = getCellValue(row.getCell(14))
            if (item['dept_code']) {
              const sql = `INSERT INTO income(dept_code, period_id, cur_day_qty, cur_day_total, cur_day_qty_s, cur_day_total1_s, last_day_qty, last_day_total, last_day_qty_s, last_day_total1_s, last_month_clledted_qty, last_month_postage_total, last_collected_qty, last_postage_total, year_collected_qty, year_postage_total, modify_date) VALUES ( '${item.dept_code}', '${curMonth}', ${item.cur_day_qty}, ${item.cur_day_total}, ${item.cur_day_qty_s}, ${item.cur_day_total1_s}, ${item.last_day_qty}, ${item.last_day_total}, ${item.last_day_qty_s}, ${item.last_day_total1_s}, ${item.last_month_clledted_qty}, ${item.last_month_postage_total}, ${item.last_collected_qty}, ${item.last_postage_total}, ${item.year_collected_qty}, ${item.year_postage_total}, now());`
              console.log(sql.replace(/undefined/g,0))
            }
          }
        })
      })
}

function parseExcelData12() {
  var workbook = new Excel.Workbook()
    workbook.xlsx.readFile('./标准20201128.xlsx')
      .then(function() {
        const worksheet = workbook.getWorksheet('机构量收分析表(个计)')
        worksheet.eachRow(function(row, rowNumber) {
          const item = {}
          if (rowNumber > 4 & rowNumber < 246) {
            item['dept_code'] = getCellValue(row.getCell(5))
            item['last_day_qty'] = getCellValue(row.getCell(7))
            item['last_day_total'] = getCellValue(row.getCell(8))
            if (item['dept_code']) {
              const sql = `update income set last_day_qty = ${item.last_day_qty},last_day_total = ${item.last_day_total} where period_id = '2020-11' and dept_code = ${item.dept_code};`
              console.log(sql)
            }
          }
        })
      })
}


function parseExcelData13() {
  var workbook = new Excel.Workbook()
    workbook.xlsx.readFile('./散户20201129.xlsx')
      .then(function() {
        const worksheet = workbook.getWorksheet('机构量收分析表(个计)')
        worksheet.eachRow(function(row, rowNumber) {
          const item = {}
          if (rowNumber > 4 & rowNumber < 178) {
            item['dept_code'] = getCellValue(row.getCell(5))
            item['cur_day_qty_s'] = getCellValue(row.getCell(7))
            item['cur_day_total1_s'] = getCellValue(row.getCell(8))
            item['last_collected_qty'] = getCellValue(row.getCell(10))
            item['last_postage_total'] = getCellValue(row.getCell(11))
            if (item['dept_code']) {
              const sql = `update income set cur_day_qty_s = ${item.cur_day_qty_s},cur_day_total1_s = ${item.cur_day_total1_s},last_collected_qty = ${item.last_collected_qty},last_postage_total = ${item.last_postage_total}  where period_id = '2020-11' and dept_code = ${item.dept_code};`
              console.log(sql)
            }
          }
        })
      })
}



function parseExcelData14() {
  var workbook = new Excel.Workbook()
    workbook.xlsx.readFile('./散户20201128.xlsx')
      .then(function() {
        const worksheet = workbook.getWorksheet('机构量收分析表(个计)')
        worksheet.eachRow(function(row, rowNumber) {
          const item = {}
          if (rowNumber > 4 & rowNumber < 195) {
            item['dept_code'] = getCellValue(row.getCell(5))
            item['last_day_qty_s'] = getCellValue(row.getCell(7))
            item['last_day_total1_s'] = getCellValue(row.getCell(8))
            if (item['dept_code']) {
              const sql = `update income set last_day_qty_s = ${item.last_day_qty_s},last_day_total1_s = ${item.last_day_total1_s} where period_id = '2020-11' and dept_code = ${item.dept_code};`
              console.log(sql)
            }
          }
        })
      })
}

setTimeout(() =>{
  parseExcelData()
},0)

setTimeout(() =>{
  parseExcelData11()
},3000)

setTimeout(() =>{
  parseExcelData12()
},6000)

setTimeout(() =>{
  parseExcelData13()
},10000)

setTimeout(() =>{
  parseExcelData14()
},15000)