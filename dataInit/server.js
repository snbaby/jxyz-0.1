const pool = require('./core/pool')
const moment = require('moment')
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

async function main(deptCode){
    // 查询组织信息
    const deptList = await pool.query(`select code,name,level from t_grid_m`)

}

function getConfig(){
    pool.query(`select b.name as level1Name, c.name as level2Name,a.name as level3Name,a.code,d.grid_code,d.level,d.value from t_layer_m a LEFT join  t_tab_m b on a.tab_code = b.code left join t_tab_group_m c on a.tab_group_code = c.code left join t_grid_statistics d on a.code = d.key where d.grid_code = '33600035'
    and c.name in ('特快专递业务量','特快专递业务收入','现费指标')`).then(data =>{
        const aaa = data.map(item =>{
            return {
                groubName: item.level1Name,
                classifyName: item.level2Name,
                name: item.level3Name,
                code: item.code
            }
        })
    })
}
function getSqlStr(str, code,level){
    switch(level){
        case 2:
            str = `${str} and city_code = '${code}'`
            break
        case 3:
            str = `${str} and area_code = '${code}'`
            break   
        case 4:
            str = `${str} and dept_code = '${code}'`
            break   
        case 5:
            str = `${str} and emp_section_code = '${code}'`
            break   
    }
    return str
}

const gridCode = '33600035'
const currMonth = moment().format('yyyy-MM')
const currDay = moment().format('yyyy-MM-DD')
const currYear = moment().format('yyyy')
normConfig.forEach(item =>{
    const code = item.code
    switch(code){
        case 'PQdIJEvH': // 当月揽收收入
            console.log('code:', code)
            getCurrMonthTotal1(gridCode,4,code)
            break;
        case 'h1c1Jjpd': // 当月揽收量
            console.log(code)
            getCurrMonthNum1(gridCode,4,code)
            break;
        case 'SdKxDdF7': // 本年累计揽收量
            console.log('code:', code)
            getCurrYearNum1(gridCode,4,code)
            break;
        case 'bZLMLt5K': // 本年累计揽收收入
            console.log('code:', code)
            getCurrYearTotal1(gridCode,4,code)
            break;
        // case 'eFKz3RDb': // 当月环比增幅
        //     getCurrMonthTotal1(gridCode,4,code)
        //     break;
        // case 'DALngQA0': // 昨日揽收收入
        //     getCurrMonthTotal1(gridCode,4,code)
        //     break;
        // case 'pcNXGE7K': // 昨日环比增幅
        //     getCurrMonthTotal1(gridCode,4,code)
        //     break;
        // case 'gaNxlBlV': // 本年环比增幅
        //     getCurrMonthTotal1(gridCode,4,code)
        //     break;
        // case 'rUsHrDlu': // 日人均揽收量
        //     getCurrMonthTotal1(gridCode,4,code)
        //     break;
        // case 'ptJaygoo': // 昨日揽收量 - 现费指标
        //     getCurrYearTotal1(gridCode,4,code)
        //     break;
        // case 'Jej5IdxX': // 昨日揽收收入 - 现费指标
        //     getCurrYearTotal1(gridCode,4,code)
        //     break;
        case 'fiM5oVr1': // 当月揽收量 - 现费指标
            getCurrMonthNum2(gridCode,4,code)
            break;
        case 'cyP04YKV': // 当月揽收收入 - 现费指标
            getCurrMonthTotal2(gridCode,4,code)
            break;
    }
})

// 查询当月揽收量 - 特快转递
function getCurrMonthNum1(code,level,key){
    const sql = getSqlStr(`select SUM(last_month_clledted_qty) as total from income where period_id = '${currMonth}'`,code,level)
    pool.query(sql).then(data =>{
        console.log(data )
        if (data && data.length > 0) {
            const sql1 = `INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${data[0].total}', '${level}', 'mainDownLeftCount', '${key}', '${data[0].total}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW())`;
            pool.query(sql1).then(data =>{
                console.log('sql:', sql)
            }).catch(e =>{
                console.log(e)
            })
        }
    })
}

// 查询当月揽收量 - 现费
function getCurrMonthNum2(code,level,key){
    const sql = getSqlStr(`select SUM(last_collected_qty) as total from income where period_id = '${currMonth}'`,code,level)
    pool.query(sql).then(data =>{
        console.log(data )
        if (data && data.length > 0) {
            pool.query(`INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${data[0].total}', '${level}', 'mainDownLeftCount', '${key}', '${data[0].total}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW())`).then(data =>{
                console.log('11111111')
            }).catch(e =>{
                console.log(e)
            })
        }
    })
}

// 查询当月揽收收入 - 特快转递
function getCurrMonthTotal1(code,level,key){
    const sql = getSqlStr(`select SUM(last_month_postage_total) as total from income where period_id = '${currMonth}'`,code,level)
    pool.query(sql).then(data =>{
        if (data && data.length > 0) {
            pool.query(`INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${data[0].total}', '${level}', 'mainDownLeftCount', '${key}', '${data[0].total}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW())`).then(data =>{
                console.log('11111111')
            }).catch(e =>{
                console.log(e)
            })
        }
    })
}

// 查询当月揽收收入 - 现费
function getCurrMonthTotal2(code,level,key){
    const sql = getSqlStr(`select SUM(last_month_postage_total) as total from income where period_id = '${currMonth}'`,code,level)
    pool.query(sql).then(data =>{
        if (data && data.length > 0) {
            pool.query(`INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${data[0].total}', '${level}', 'mainDownLeftCount', '${key}', '${data[0].total}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW())`).then(data =>{
                console.log('11111111')
            }).catch(e =>{
                console.log(e)
            })
        }
    })
}




// 查询当年揽收量 - 特快转递
function getCurrYearNum1(code,level,key){
    const sql = getSqlStr(`select SUM(last_month_clledted_qty) as total from income where period_id like '${currYear}%'`,code,level)
    pool.query(sql).then(data =>{
        if (data && data.length > 0) {
            pool.query(`INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${data[0].total}', '${level}', 'mainDownLeftCount', '${key}', '${data[0].total}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW())`).then(data =>{
                console.log('11111111')
            }).catch(e =>{
                console.log(e)
            })
        }
    })
}


// 查询当年揽收收入 - 特快转递
function getCurrYearTotal1(code,level,key){
    const sql = getSqlStr(`select SUM(last_month_postage_total) as total from income where period_id like '${currYear}%'`,code,level)
    pool.query(sql).then(data =>{
        if (data && data.length > 0) {
            pool.query(`INSERT INTO t_grid_statistics(grid_code, level, type, `+'`key`' +`, value, `+'`group`' +`, remark, statistics_time, create_user, create_date, modify_user, modify_date) VALUES ( '${data[0].total}', '${level}', 'mainDownLeftCount', '${key}', '${data[0].total}', NULL, NULL, '${currDay}', 'system', NOW(), 'system', NOW())`).then(data =>{
                console.log('11111111')
            }).catch(e =>{
                console.log(e)
            })
        }
    })
}