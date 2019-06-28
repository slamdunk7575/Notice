package notice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notice.domain.NoticeRedis;
import notice.service.NoticeRedisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
// @Controller
public class NoticeRedisController {

    private NoticeRedisService noticeRedisService;
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/api/notice")
    public ModelAndView openNoticeList(ModelMap model) throws Exception {

        ModelAndView mv = new ModelAndView("/notice/noticeList");
        List<NoticeRedis> list = noticeRedisService.selectNoticeList();

        mv.addObject("list", list);

        return mv;
    }


    // 페이징 처리
    @GetMapping(value = "/api/noticePage")
    public ModelAndView openNoticeListWithPage(@PageableDefault Pageable pageable, ModelMap model) throws Exception {

        ModelAndView mv = new ModelAndView("/notice/noticeList");
        Page<NoticeRedis> list = noticeRedisService.selectNoticeListWithPage(pageable);

        list.get().sorted().collect(Collectors.toList());
        /*Collections.sort(list, new Comparator<NoticeRedis>() {
            @Override
            public int compare(NoticeRedis n1, NoticeRedis n2) {
                if (n1.getNoticeIdx() < n2.getNoticeIdx()) {
                    return -1;
                } else if (n1.getNoticeIdx() > n2.getNoticeIdx()) {
                    return 1;
                }
                return 0;
            }
        });*/


        mv.addObject("list", list);

        log.debug("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                list.getTotalElements(), list.getTotalPages(), list.getSize(),
                list.getNumber(), list.getNumberOfElements());

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
        noticeRedisService.saveNotice(notice, multipartHttpServletRequest);
        return "redirect:/api/noticePage";
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
