//package com.ssafy.eggmoney.news.service;
//
//import com.ssafy.eggmoney.news.dto.response.NewsCrawlResponse;
//import com.ssafy.eggmoney.news.dto.response.NewsTitlesResponse;
//import com.ssafy.eggmoney.news.dto.response.SummarizedContentResponse;
//import com.ssafy.eggmoney.news.entity.News;
//import com.ssafy.eggmoney.news.repository.NewsRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//@Transactional
//@Rollback(value = false)
//class NewsServiceImplTest {
//    @Autowired
//    private NewsService newsService;
//    @Autowired
//    private NewsRepository newsRepository;
//
//    @Test
//    void crawlFinanceHeadLineNews() {
//        List<NewsCrawlResponse> headLineNews = newsService.crawlFinanceHeadLineNews();
//
//        Assertions.assertThat(headLineNews).hasSizeGreaterThan(5);
//    }
//
//    @Test
//    void crawlNewsContent() {
//        List<NewsCrawlResponse> headLineNewsList = newsService.crawlFinanceHeadLineNews();
//        NewsCrawlResponse headLineNews = headLineNewsList.get(0);
//        String newsContent = newsService.crawlNewsContent(headLineNews.getLink());
//
//        Assertions.assertThat(newsContent).hasSizeGreaterThan(100);
//    }
//
//    @Test
//    void SummarizeAndSaveFinanceNews() {
//        List<News> newsList = new ArrayList<>();
//        List<NewsCrawlResponse> headLineNewsList = newsService.crawlFinanceHeadLineNews();
//
//        for (int i = 0; i < 5; i++) {
//            News news = new News(headLineNewsList.get(i).getTitle(),
//                    headLineNewsList.get(i).getLink(), headLineNewsList.get(i).getPress());
//            String newsContent = newsService.crawlNewsContent(headLineNewsList.get(i).getLink());
//            newsContent = "줄바꿈 X, 350토큰이상, 존댓말. 뉴스: " + newsContent;
//            SummarizedContentResponse summarizedContent = newsService.summarizeNews(newsContent);
//            news.addContent(summarizedContent.getContent());
//            newsList.add(news);
//        }
//
//        List<News> beforeFindNewsList = newsRepository.findAll();
//
//        newsService.saveAllNews(newsList);
//
//        List<News> findNewsList = newsRepository.findAll();
//
//        Assertions.assertThat(findNewsList.size() - beforeFindNewsList.size())
//                .isEqualTo(newsList.size());
//    }
//
//    @Test
//    public void findTitleList() {
//        List<NewsTitlesResponse> titleList = newsService.findNewsTitles();
//
//        Assertions.assertThat(titleList).isNotEmpty();
//    }
//}