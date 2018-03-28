# QuickHttp


### 1. Example

```java
QuickClient client = new QuickClient.Builder().Build();
```

​	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发起请求

```java
client.get("http://www.baidu.com", new StringCallback() {
            @Override
            void onSuccess(String s) {

            }

            @Override
            void onError(int code, String error) {

            }
        });
```

​	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自动映射到对象

```java
client.post("url", new ModelCallback<Demo>() {
            @Override
            void onSuccess(Demo demo) {
                
            }

            @Override
            void onError(int code, String error) {

            }
        });
```



### 2. 处理固定响应格式

​	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;通常API响应的格式如下

```json
{
  "errCode": 0,
  "errMsg": "",
  "data": {
    ...
  }
}
```

​	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;将字段data映射到不同对象，调用如下：

```java
//在response模板中，增加注解@Code,@Error,@Data
public class BaseResponse {
    @Code
    public int errCode;
    @Error
    public String errMsg;
    @Data
    public String data;
}

//将模板设置到client
QuickClient client = new QuickClient.Builder()
                .setTemplate(BaseResponse.class)
                .enableLog(true)
                .Build();
                
//在回调泛型中指定data需要映射的对象类型
client.post("url", new ModelCallback<DataBean>() {
            @Override
            void onSuccess(DataBean dataBean) {

            }

            @Override
            void onError(int code, String error) {

            }
        });
```

​	注：callback&lt;T&gt;必须是具体class，不能在包含泛型。如~~List&lt;String&gt;~~



### 3. 取消请求

```java
Object tag = new Object();

client.get(tag, "http://www.baidu.com", new StringCallback() {
            @Override
            void onSuccess(String s) {

            }

            @Override
            void onError(int code, String error) {

            }
        });

client.cancel(tag);
```

### 4. 同步请求

```java
client.sync().get(tag, "http://www.baidu.com", new StringCallback() {
            @Override
            void onSuccess(String s) {

            }

            @Override
            void onError(int code, String error) {

            }
        });
```