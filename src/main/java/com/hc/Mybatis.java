package com.hc;


import com.hc.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;


import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface UserMapper {
    @Select("select * form aicad_user where id = #{id} and name = #{name}")
    List<User> selectUsers(Integer id,String name);

    @Select("select id form aicad_user")
    User selectUser();
}

/**
 * @Descrption: Mybatis
 * @Author: HC
 * @Date: 2020/8/28 11:20
 */
@Slf4j
public class Mybatis {
    public static void main(String[] args) {
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(Mybatis.class.getClassLoader(), new Class<?>[]{UserMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Map<String,Object> map =  buildMethodArgsNameMap(method,args);
                Select annotation = method.getAnnotation(Select.class);
                if(annotation!=null){
                    for (String s:annotation.value()) {
                        parseSQL(s,map);
                        System.out.println("输出sql"+s);
                    }
                }


                return null;
            }
        });
        userMapper.selectUsers(1,"a");
        userMapper.selectUser();
    }

    /**
     *
     * @param sql
     * @param nameArgsMap
     * @return
     */
    public static String parseSQL(String sql, Map<String, Object> nameArgsMap) {
        String parseSQL = "";
        StringBuilder stringBuilder = new StringBuilder();
        int length = sql.length();
        for (int i = 0; i < length; i++) {
            if (sql.charAt(i) == '#') {
                int nextIndex = i + 1;
                char nextChar = sql.charAt(nextIndex);
                if (nextChar != '{') {
                    throw new RuntimeException(String.format("这里应该为#{\nsql:%s\nindex:%d", stringBuilder.toString(), nextIndex));
                }
                StringBuilder argSB = new StringBuilder();
               // i = parseSQLArg(argSB,sql,nextIndex);
                String argName = argSB.toString();
                System.out.println(argName);
                Object argValue = nameArgsMap.get(argName);
                stringBuilder.append(argValue);
                continue;
            }stringBuilder.append(sql.charAt(i));
        }
        return null;
    }

//    private static int parseSQLArg(StringBuilder argSB, String sql, int nextIndex) {
//
//    }

    /**
     * 通过方法拿到参数
     * @param method
     * @param args
     * @return
     */
    public static Map<String, Object> buildMethodArgsNameMap(Method method, Object[] args) {
        HashMap<String ,Object> nameArgsMap = new HashMap<>(16);
        Parameter[] parameters = method.getParameters();
        Arrays.asList(parameters).forEach(parameter -> {
            String name = parameter.getName();
            System.out.println("输出参数"+name);
        });
        return nameArgsMap;
    }
}
