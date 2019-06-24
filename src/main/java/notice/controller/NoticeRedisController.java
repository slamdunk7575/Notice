package notice.controller;

import notice.entity.NoticeRedis;
import notice.service.NoticeRedisService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class NoticeRedisController {

    private NoticeRedisService noticeRedisService;

    @Autowired
    public NoticeRedisController(NoticeRedisService noticeRedisService) {
        this.noticeRedisService = noticeRedisService;
    }

    @GetMapping(value = "/api/notice")
    public ModelAndView openNoticeList(ModelMap model) throws Exception {

        ModelAndView mv = new ModelAndView("/notice/noticeList");
        List<NoticeRedis> list = noticeRedisService.selectNoticeList();
        mv.addObject("list", list);

        return mv;
    }

    // @GetMapping(value = "/api/notice/write")
    @RequestMapping(value="/api/notice/write", method= RequestMethod.GET)
    public String openNoticeWrite() throws Exception {
        return "/notice/noticeWrite";
    }

    // @PostMapping(value = "/api/notice/write")
    @RequestMapping(value="/api/notice/write", method=RequestMethod.POST)
    public String writeNotice(NoticeRedis notice, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        System.out.println(notice);
        noticeRedisService.saveNotice(notice, multipartHttpServletRequest);
        return "redirect:/api/notice";
    }

    @GetMapping(value = "/api/notice/{noticeIdx}")
    public ModelAndView openNoticeDetail(@PathVariable("noticeIdx") int noticeIdx) throws Exception {

        ModelAndView mv = new ModelAndView("/notice/noticeDetail");
        NoticeRedis notice = noticeRedisService.selectNoticeDetail(noticeIdx);
        mv.addObject("notice", notice);

        return mv;
    }

    @PutMapping(value = "/api/notice/{noticeIdx}")
    public String updateNotice(NoticeRedis notice) throws Exception {
        noticeRedisService.saveNotice(notice, null);
        return "redirect:/api/notice";
    }

    @DeleteMapping(value = "/api/notice/{noticeIdx}")
    public String deleteNotice(@PathVariable("noticeIdx") int noticeIdx) throws Exception {
        noticeRedisService.deleteNotice(noticeIdx);
        return "redirect:/api/notice";
    }

    /*@RequestMapping(value="/api/notice/file", method=RequestMethod.GET)
    public void downloadNoticeFile(int noticeIdx, int idx, HttpServletResponse response) throws Exception{
        NoticeFileEntity file = noticeService.selectNoticeFileInformation(noticeIdx, idx);

        byte[] files = FileUtils.readFileToByteArray(new File(file.getStoredFilePath()));

        response.setContentType("application/octet-stream");
        response.setContentLength(files.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getOriginalFileName(),"UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(files);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }*/
}
