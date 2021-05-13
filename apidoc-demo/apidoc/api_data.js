define({ "api": [
  {
    "type": "GET",
    "url": "/apiDoc/getTest",
    "title": "getApiTest",
    "version": "1.0.0",
    "group": "ApidocTestController",
    "name": "getApiTest",
    "parameter": {
      "fields": {
        "请求体": [
          {
            "group": "请求体",
            "type": "String",
            "optional": false,
            "field": "requestBody",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "请求体示例",
          "content": "\"WU\"",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "响应结果": [
          {
            "group": "响应结果",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "响应结果示例",
          "content": "\"2RaLz2\"",
          "type": "json"
        }
      ]
    },
    "filename": "src/main/java/com/gallop/apidoc/controller/ApidocTestController.java",
    "groupTitle": "ApidocTestController"
  },
  {
    "type": "GET",
    "url": "/apiDoc/{sn}/rt-info",
    "title": "getUeRealTimeInfo",
    "version": "1.0.0",
    "group": "ApidocTestController",
    "name": "getUeRealTimeInfo",
    "parameter": {
      "fields": {
        "请求参数": [
          {
            "group": "请求参数",
            "type": "Number",
            "optional": false,
            "field": "userId",
            "description": ""
          },
          {
            "group": "请求参数",
            "type": "String",
            "optional": false,
            "field": "sn",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例",
          "content": "sn=FhYcee&userId=8822",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "响应结果": [
          {
            "group": "响应结果",
            "type": "String",
            "optional": false,
            "field": "sn",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "status",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "soc",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "voltage",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "current",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "temperature",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "reportTime",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "响应结果示例",
          "content": "{\"current\":5154.98,\"soc\":7805.08,\"temperature\":6548.74,\"sn\":\"hdQBG\",\"status\":6719,\"voltage\":2734.92,\"reportTime\":3504994926819}",
          "type": "json"
        }
      ]
    },
    "filename": "src/main/java/com/gallop/apidoc/controller/ApidocTestController.java",
    "groupTitle": "ApidocTestController"
  },
  {
    "type": "POST",
    "url": "/apiDoc/queryData",
    "title": "queryData 查询区域数据",
    "version": "1.1.0",
    "group": "ApidocTestController",
    "name": "queryData",
    "parameter": {
      "fields": {
        "请求体": [
          {
            "group": "请求体",
            "type": "String",
            "optional": false,
            "field": "dateStart",
            "description": "<p>开始时间</p>"
          },
          {
            "group": "请求体",
            "type": "String",
            "optional": false,
            "field": "dateEnd",
            "description": "<p>结束时间</p>"
          },
          {
            "group": "请求体",
            "type": "String",
            "optional": false,
            "field": "areaNumber",
            "description": "<p>区域号码</p>"
          },
          {
            "group": "请求体",
            "type": "String",
            "optional": false,
            "field": "rankCategory",
            "description": "<p>范围类型</p>"
          },
          {
            "group": "请求体",
            "type": "String",
            "optional": false,
            "field": "areaQueryType",
            "description": "<p>区域查询类型</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "请求体示例",
          "content": "{\"rankCategory\":\"vnBYr\",\"dateStart\":\"YHc8C8\",\"areaNumber\":\"l826xt\",\"areaQueryType\":\"pKi0NpoT\",\"dateEnd\":\"d\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "响应结果": [
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "Boolean",
            "optional": false,
            "field": "success",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "Object",
            "optional": false,
            "field": "entity",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "String",
            "optional": false,
            "field": "sn",
            "description": "<p>设备序列号</p>"
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "status",
            "description": "<p>设备状态</p>"
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "soc",
            "description": "<p>电池电量百分比</p>"
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "voltage",
            "description": "<p>电池电压</p>"
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "current",
            "description": "<p>电池电流</p>"
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "temperature",
            "description": "<p>电池温度</p>"
          },
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "reportTime",
            "description": "<p>上报时间</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "响应结果示例",
          "content": "{\"code\":848,\"success\":false,\"message\":\"CqpTk5P\",\"entity\":{\"current\": 5154.98,\n    \"soc\": 7805.08,\n    \"temperature\": 6548.74,\n    \"sn\": \"hdQBG\",\n    \"status\": 6719,\n    \"voltage\": 2734.92,\n    \"reportTime\": 3504994926819\n}}",
          "type": "json"
        }
      ]
    },
    "filename": "src/main/java/com/gallop/apidoc/controller/ApidocTestController.java",
    "groupTitle": "ApidocTestController"
  },
  {
    "type": "POST",
    "url": "/apiDoc/queryHome",
    "title": "queryHome",
    "version": "1.0.0",
    "group": "ApidocTestController",
    "name": "queryHome",
    "description": "<p>查询首页数据</p>",
    "parameter": {
      "fields": {
        "请求参数": [
          {
            "group": "请求参数",
            "type": "Object",
            "optional": false,
            "field": "bindingResult",
            "description": ""
          }
        ],
        "请求体": [
          {
            "group": "请求体",
            "type": "String",
            "optional": false,
            "field": "dateStart",
            "description": ""
          },
          {
            "group": "请求体",
            "type": "String",
            "optional": false,
            "field": "dateEnd",
            "description": ""
          },
          {
            "group": "请求体",
            "type": "String",
            "optional": false,
            "field": "areaNumber",
            "description": ""
          },
          {
            "group": "请求体",
            "type": "String",
            "optional": false,
            "field": "rankCategory",
            "description": ""
          },
          {
            "group": "请求体",
            "type": "String",
            "optional": false,
            "field": "areaQueryType",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "请求参数示例",
          "content": "bindingResult=null",
          "type": "json"
        },
        {
          "title": "请求体示例",
          "content": "{\"rankCategory\":\"A\",\"dateStart\":\"F2P\",\"areaNumber\":\"6Ew\",\"areaQueryType\":\"Of2\",\"dateEnd\":\"mvPdC\"}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "响应结果": [
          {
            "group": "响应结果",
            "type": "Number",
            "optional": false,
            "field": "code",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "Boolean",
            "optional": false,
            "field": "success",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": ""
          },
          {
            "group": "响应结果",
            "type": "Object",
            "optional": false,
            "field": "entity",
            "description": ""
          }
        ]
      },
      "examples": [
        {
          "title": "响应结果示例",
          "content": "{\"code\":1150,\"success\":false,\"message\":\"Pu3\",\"entity\":{}}",
          "type": "json"
        }
      ]
    },
    "filename": "src/main/java/com/gallop/apidoc/controller/ApidocTestController.java",
    "groupTitle": "ApidocTestController"
  }
] });
