global.pool = require('./core/pool')

global.execSql = async function (sql) {
    const result = await global.pool.query(sql);
    return result
  }
const excelServer = require('./excelServer') // 解析excel表
const excelServerList = require('./excelServerList') // 源数据随机分解到各个段道
const serverInit = require('./serverInit') // 初始化营业部指标数据
const serverSum = require('./serverSum') // 统计省市区各级累加数据
const randomServer = require('./randomServer') // 生成各段道指标数据
const randomRJServer = require('./randomRJServer') // 计算日人均揽件量


process.on('uncaughtException',function(err){
    console.log("uncaughtException")
}) //监听未捕获的异常
 
process.on('unhandledRejection',function(err,promise){
    console.log("unhandledRejection", err)
}) 

excelServer.parseExecl().then(res =>{
    console.log('EXCEL解析成功')
    excelServerList.parseList().then(res =>{
        console.log('EXCEL明细记录解析成功')
    })
    serverInit.init().then(res =>{
        console.log('数据初始化成功')
        serverSum.sumData().then(res =>{
            console.log('省市区合计成功')
            randomServer.randomData().then(res =>{
                console.log('段道数据生成成功')
            })
            randomRJServer.randomData().then(res =>{
                console.log('日平均数据生成成功')
            })
        })
    })
})


