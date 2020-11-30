const pool = require('./core/pool')
const moment = require('moment')
process.on('uncaughtException',function(err){
    console.log("uncaughtException")
}) //监听未捕获的异常
 
process.on('unhandledRejection',function(err,promise){
    console.log("unhandledRejection", err)
}) 
const lastMonth = moment().month(moment().month() - 1).startOf('month').format("YYYY-MM");
const curMonth = moment().add(0, 'month').format('YYYY-MM')
const normConfig =[
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '当月揽收收入(万元)',
      code: 'PQdIJEvH'
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务量',
      name: '当月揽收量(件)',
      code: 'h1c1Jjpd'
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务量',
      name: '本年累计揽收量(件)',
      code: 'SdKxDdF7'
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '当月环比增幅',
      code: 'eFKz3RDb'
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '昨日揽收收入(万元)',
      code: 'DALngQA0'
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '昨日环比增幅',
      code: 'pcNXGE7K'
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '本年累计揽收收入(万元)',
      code: 'bZLMLt5K'
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务收入',
      name: '本年环比增幅',
      code: 'gaNxlBlV'
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务量',
      name: '日人均揽收量(件)',
      code: 'rUsHrDlu'
    },
    {
      groubName: '作战图',
      classifyName: '特快专递业务量',
      name: '昨日揽收量(件)',
      code: 'T0Uk9Gf3'
    },
    {
      groubName: '现费指标',
      classifyName: '现费指标',
      name: '昨日揽收量(件)',
      code: 'ptJaygoo'
    },
    {
      groubName: '现费指标',
      classifyName: '现费指标',
      name: '昨日揽收收入(万元)',
      code: 'Jej5IdxX'
    },
    {
      groubName: '现费指标',
      classifyName: '现费指标',
      name: '当月揽收量(件)',
      code: 'fiM5oVr1'
    },
    {
      groubName: '现费指标',
      classifyName: '现费指标',
      name: '当月揽收收入(万元)',
      code: 'cyP04YKV'
    }
  ]

  async function execSql(sql) {
    await pool.query(sql);
  }
  async function main4(){
    normConfig.forEach(item =>{
        execSql('select count(1) from t_grid_statistics where `key` = "'+item.code+'"')
        console.log('sssss')
    })
    console.log('dddddddd')
    return false
  }
  
//   main4()
//   setTimeout(() =>{
//     console.log('close')
//   },15000)