package cn.hysf.web.GoodsController;

import cn.hysf.Page.PageRequest;
import cn.hysf.Page.PageResult;
import cn.hysf.model.Goods;
import cn.hysf.model.User;
import cn.hysf.service.GoodsService;
import cn.hysf.util.FileUpLoadUtil;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.*;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/findAllGoods")
    @ResponseBody
    public Set<Goods> findAllGoods(){
        List<Goods> allGoods = goodsService.findAllGoods();
        Set<Goods> allGoodsSet = new HashSet<>();
        for (int i = 0; i < allGoods.size(); i++) {
            allGoodsSet.add(allGoods.get(i));
        }
        return allGoodsSet;
    }

    @RequestMapping("/findGoodsByGid")
    @ResponseBody
    public Goods findInfoByGid(String gid){
        return goodsService.findGoodByGid(gid);
    }

    @RequestMapping("/findTypeRecommendGoods")
    @ResponseBody
    public Set<Goods> findTypeRecommendGoods(String gid){
        //根据gid查找这个商品信息
        Goods good = goodsService.findGoodByGid(gid);
        String gkind = good.getGkind();
        //根据gkind种类来查找这个种类的商品信息
        List<Goods> goodsByKind = goodsService.findGoodsByKind(gkind);
        //去重
        for (int i = 0; i < goodsByKind.size(); i++) {
            Goods goods = goodsByKind.get(i);
            Integer gid1 = goods.getGid();
            int i1 = Integer.parseInt(gid);
            if(i1==gid1){
                goodsByKind.remove(goods);
            }
        }
        Set<Goods> goodsSet = new HashSet<>();
        for (int i = 0; i < goodsByKind.size(); i++) {
            goodsSet.add(goodsByKind.get(i));
        }
        return goodsSet;
    }

    @RequestMapping("/findRecommendGoods")
    @ResponseBody
    public Set<Goods> findRecommendGoods(String gid,HttpServletRequest request){
        //如果未登录 推荐最受欢迎的商品
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        if(user==null){
            //查找热门商品Goods  , 除了本商品
            Set<Goods> goods = goodsService.findHotGoods(gid);
            return goods;
        }
        //协同过滤算法
        return goodsService.findUserRecommendsGoods(gid,user);
    }

    //找到所有男士的商品  除了mankind=1的商品
    @RequestMapping("/findManTypeGoods")
    @ResponseBody
    public Set<Goods> findManTypeGoods(){
        List<Goods> list = goodsService.findManTypeGoods();
        Set<Goods> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i));
        }
        return set;
    }

    @RequestMapping("/findWomanTypeGoods")
    @ResponseBody
    public Set<Goods> findWomanTypeGoods(){
        List<Goods> list = goodsService.findWomanTypeGoods();
        Set<Goods> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i));
        }
        return set;
    }

    @RequestMapping("/findOtherTypeGoods")
    @ResponseBody
    public Set<Goods> findOtherTypeGoods(){
        List<Goods> list = goodsService.findOtherTypeGoods();
        Set<Goods> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i));
        }
        return set;
    }

    @RequestMapping("/findUnder100Goods")
    @ResponseBody
    public Set<Goods> findUnder100Goods(){
        List<Goods> list = goodsService.findUnder100Goods();
        Set<Goods> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i));
        }
        return set;
    }

    @RequestMapping(value="/findPage")
    @ResponseBody
    public Object findPage(PageRequest pageQuery) {
        PageResult page = goodsService.findPage(pageQuery);
        return page;
    }

    @RequestMapping("/changeGoodStatusByGid")
    @ResponseBody
    public void changeGoodStatusByGid(String gid){
        goodsService.changeGoodStatusByGid(gid);
    }

    @RequestMapping("/addGoods1")
    @ResponseBody
    public void addGoods(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        StringBuilder sb = new StringBuilder();
        //判断是否是multipart请求
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new RuntimeException("当前请求不支持文件上传");
        }
        //创建一个FileItem工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //创建文件上传核心组件
        ServletFileUpload upload = new ServletFileUpload(factory);
        //解析请求，获取所有的item
        List<FileItem> items = upload.parseRequest(request);
        //遍历fileItems
        for (FileItem item : items) {
            if (item.isFormField()) {
                //若为普通表单项
                //获取表单项名称
                String fieldName = item.getFieldName();
                //获取表单项的值
                String fieldValue = item.getString();
                //System.out.println(fieldName+" = "+fieldValue);
                sb.append(fieldValue + "/");
            } else {
                //若item是文件表单项
                //获取上传文件原始名称
                String fileName = item.getName();
                //System.out.println(fileName);
                sb.append(fileName + "/");
                //获取输入流，其中有上传文件的内容
                InputStream is = item.getInputStream();
                //获取文件保存在服务器的路径
                String path = "H:\\360MoveData\\Users\\LENOVO\\Desktop\\\\fashionShop\\src\\main\\resources\\static\\img\\goodsImg";
                //创建目标文件，将来用于保存上传文件
                File descFile = new File(path, fileName);
                //创建文件输出流
                OutputStream os = new FileOutputStream(descFile);
                //将输入流中的数据，写入到输出流中（*文件复制）
                int len = -1;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                //关闭流
                os.close();
                is.close();
            }
        }
        //去掉最后一个逗号
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        String str = URLDecoder.decode(sb.toString(), "UTF-8");
        String[] split = str.split("/");
        for (String s : split) {
            System.out.println(s);
        }
        Goods good = new Goods();


    }

    @PostMapping("/addGoods")
    public String addGoods(String gname1, String gname2, String ginfo, String gprice, String gkind, MultipartFile gimg1, MultipartFile gimg2, MultipartFile gimg3, MultipartFile gimg4, MultipartFile gimg5, MultipartFile gimg6, MultipartFile gimg7, MultipartFile gimg8, HttpServletRequest request, Model model, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize){

        Goods good = new Goods();
        good.setGname1(gname1);
        good.setGname2(gname2);
        good.setGprice(Double.parseDouble(gprice));
        good.setGinfo(ginfo);
        good.setGkind(gkind);
        if(!StringUtil.isEmpty(gimg1.getOriginalFilename())){
            String fileName = UUID.randomUUID().toString().replace("-", "")+gimg1.getOriginalFilename();
            String gimg11 = "img/goodsImg/" + fileName;
            FileUpLoadUtil.uploadFileTest(gimg1,fileName);
            good.setGimg1(gimg11);
        }
        if(!StringUtil.isEmpty(gimg2.getOriginalFilename())){
            String fileName = UUID.randomUUID().toString().replace("-", "")+gimg2.getOriginalFilename();
            String gimg22 = "img/goodsImg/" + fileName;
            FileUpLoadUtil.uploadFileTest(gimg2,fileName);
            good.setGimg2(gimg22);
        }
        if(!StringUtil.isEmpty(gimg3.getOriginalFilename())){
            String fileName = UUID.randomUUID().toString().replace("-", "")+gimg3.getOriginalFilename();
            String gimg33 = "img/goodsImg/" + fileName;
            FileUpLoadUtil.uploadFileTest(gimg3,fileName);
            good.setGimg3(gimg33);
        }
        if(!StringUtil.isEmpty(gimg4.getOriginalFilename())){
            String fileName = UUID.randomUUID().toString().replace("-", "")+gimg4.getOriginalFilename();
            String gimg44 = "img/goodsImg/" + fileName;
            FileUpLoadUtil.uploadFileTest(gimg4,fileName);
            good.setGimg4(gimg44);
        }
        if(!StringUtil.isEmpty(gimg5.getOriginalFilename())){
            String fileName = UUID.randomUUID().toString().replace("-", "")+gimg5.getOriginalFilename();
            String gimg55 = "img/goodsImg/" + fileName;
            FileUpLoadUtil.uploadFileTest(gimg5,fileName);
            good.setGimg5(gimg55);
        }
        if(!StringUtil.isEmpty(gimg6.getOriginalFilename())){
            String fileName = UUID.randomUUID().toString().replace("-", "")+gimg6.getOriginalFilename();
            String gimg66 = "img/goodsImg/" + fileName;
            FileUpLoadUtil.uploadFileTest(gimg6,fileName);
            good.setGimg6(gimg66);
        }
        if(!StringUtil.isEmpty(gimg7.getOriginalFilename())){
            String fileName = UUID.randomUUID().toString().replace("-", "")+gimg7.getOriginalFilename();
            String gimg77 = "img/goodsImg/" + fileName;
            FileUpLoadUtil.uploadFileTest(gimg7,fileName);
            good.setGimg7(gimg77);
        }
        if(!StringUtil.isEmpty(gimg8.getOriginalFilename())){
            String fileName = UUID.randomUUID().toString().replace("-", "")+gimg8.getOriginalFilename();
            String gimg88 = "img/goodsImg/" + fileName;
            FileUpLoadUtil.uploadFileTest(gimg8,fileName);
            good.setGimg8(gimg88);
        }

        System.out.println(good);
        //调用service方法插入一条goods数据
        good.setGsize1("ONE SIZE");
        good.setGsize2("ONE SIZE");
        good.setOrdernumbers(0);
        good.setLikenumbers(0);
        good.setMankind(2);
        good.setStatus(0);
        goodsService.addGood(good);


        /*PageRequest pageQuery = new PageRequest();
        pageQuery.setPageSize(0);
        pageQuery.setPageNum(5);
        PageResult page = goodsService.findPage(pageQuery);
        List<Goods> content = (List<Goods>) page.getContent();
        page.setContent(content);
        model.addAttribute("goods",content);
        model.addAttribute("page",page);*/
        return "redirect:/declarePage/goodsPage";
    }
}
