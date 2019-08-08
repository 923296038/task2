package com.controller;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.service.StudentService;
import com.pojo.Student;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class StudentController {
//    @Autowired
//    StringUtil stringUtil;
    @Autowired
    StudentService studentService;
    private static final Logger log= LogManager.getLogger(StudentController.class);
    /**
     * 跳转到学生添加界面
     */
    @RequestMapping(value = "toStudentMore")
    public String toAddStudent(){
        log.info("1");
        return "StudentMore";
    }

    //查询,带校验
    //bindingResult接受错误
    @RequestMapping(value="StudentByName",method = RequestMethod.GET)
    public String findStudent(Model model,HttpServletRequest request, String name,
                              @Validated Student student, BindingResult bindingResult){

        if(name.length()==0&& bindingResult.hasErrors()){
            //输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError:allErrors){
                log.error(objectError.getDefaultMessage());
            }
            //将错误信息传至页面
            model.addAttribute("allErrors",allErrors);
            //出错则重新至商品更新页面
            log.error(bindingResult);
            return "error";
        }
        log.error(bindingResult);
        List<Student> studentList=studentService.findStudentByName(name);
        //System.out.println(studentList);
        //addAttribute提交到前台,视图层才能显示数据
        model.addAttribute(studentList);
        return "ChosenOne";
    }

    @RequestMapping(value = "StudentMore")
    public String addStudent(Model model,HttpServletRequest request,@Validated Student student,BindingResult bindingResult) {
        if(student.getName().length()==0||student.getStudent_id()<1||bindingResult.hasErrors()){
            //输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError:allErrors){
                log.error(objectError.getDefaultMessage());
            }
            //将错误信息传至页面
            model.addAttribute("allErrors",allErrors);
            //出错则重新至商品更新页面
            log.error("执行了if语句块");
            return "error";
        }
        student.setCreate_time(System.currentTimeMillis());
        student.setUpdate_time(System.currentTimeMillis());
        studentService.insertStudent(student);
        model.addAttribute("student", student);
        return "insertSuccess";
    }

    /**
     参数model是什么意思?
     */
    @RequestMapping(value="toStudentInfo/{id}",
            method = RequestMethod.GET)
    public String toUpdateStudent
            (Model model,@PathVariable long id){
        //先获得原数据才能更改
        Student student=studentService.findStudentById(id);
        log.error(student.getUpdate_time());
        //model.addAttibute 往前台传数据
        model.addAttribute("student",student);
        return "StudentInfo";
    }

    @RequestMapping(value = "StudentInfo",
            method = RequestMethod.POST)
    public String updateStudent(Model model,HttpServletRequest request,@Validated Student student,BindingResult bindingResult){
        if(student.getName().length()==0||student.getStudent_id()<1||bindingResult.hasErrors()){
//输出错误信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError:allErrors){
                log.error(objectError.getDefaultMessage());
            }
//将错误信息传至页面
            model.addAttribute("allErrors",allErrors);
//出错则重新至商品更新页面
            log.error("执行了if语句块");
            return "error";
        }
        student.setUpdate_time(System.currentTimeMillis());
        boolean flag = studentService.updateStudent(student);
        if(flag=true)
        {return "updateSuccess";}
        else return "error";
    }

    //查询一个学生
    /*@RequestMapping(value="StudentById",method = RequestMethod.GET)
    public String findStudent(Model model, String name,
              @Validated Student student, BindingResult result){
        if (name==null && result.hasErrors()){
            List<ObjectError>allErrors=result.getAllErrors();
            for (ObjectError objectError:allErrors) {
                System.out.println(objectError.getDefaultMessage());
            }
            model.addAttribute("errors",allErrors);
            return "/error.jsp";
        }
        List<Student> studentList=studentService.findStudentByName(name);
        //System.out.println(studentList);
        //addAttribute提交到前台,视图层才能显示数据
        model.addAttribute(studentList);
        return "ChosenOne";
    }*/

    //删除
    @RequestMapping(value = "StudentLesser/{id}",method = RequestMethod.DELETE)
    public String deleteStudent(@PathVariable long id,@Validated Student student,BindingResult result){
        boolean flag = studentService.deleteStudent(id);
        log.info("1");
        if (flag = true){return "redirect:/actionSucceed";}
        else return "redirect:/actionFailed";
    }

    @RequestMapping(value = "actionSucceed",method = RequestMethod.GET)
    public String aS(){
        return "deleteSuccess";
    }
    @RequestMapping(value = "actionFailed",method = RequestMethod.GET)
    public String aF(){
        return "error";
    }
    @RequestMapping(value = "AllStudent", method =RequestMethod.GET)
    public String findAllStudent(Model model,
            @RequestParam(defaultValue = "1",required = true,
                    value = "pageNo") Integer pageNo)  {
        Integer pageSize=5;//每页显示记录数为5
        PageHelper.startPage(pageNo,pageSize);
        List<Student>studentList=studentService.findAllStudent();//获取所有用户信息

        PageInfo<Student> pageInfo =new PageInfo<Student>(studentList);
        model.addAttribute("pageInfo",pageInfo);
        return "AllStudent";
    }

    //用jsontaglib返回查询一个人
    //通过@ResponseBody注解的方式,方法返回的对象不会经过视图解析器的渲染
    /*@RequestMapping(value = "findStudent_jstl",method = RequestMethod.GET)
    public @ResponseBody JSONObject jsonDemo()throws JSONException{
        Student student = studentService.findStudentById(180);
        log.error(student);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",student.getName());
        jsonObject.put("wish",student.getWish());
        return jsonObject;
        //报no converter错误
    }*/

    //测试ResponseBody
    @RequestMapping("testJson")
    @ResponseBody
    public HashMap testJ()
    {
        HashMap student = new HashMap();
        student.put("student.id ", "1");
        student.put("student.name ", "王全");
        student.put("student.school ", "hbpu");
        student.put("student.major ", "java");
        return student;
    }

    //使用json tag-lib生成假数据.
    @RequestMapping("testJson2")
    public String selectAll(Model model) {
        List<Student> students = studentService.findAllStudent();
        model.addAttribute("code", "200");
        model.addAttribute("message", "传递成功");
        model.addAttribute("students", students);
        return "json";
    }

}