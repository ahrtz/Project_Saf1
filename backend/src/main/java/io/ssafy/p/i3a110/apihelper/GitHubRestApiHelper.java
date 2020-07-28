package io.ssafy.p.i3a110.apihelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommitQueryBuilder;
import org.kohsuke.github.GHPerson;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositoryStatistics;
import org.kohsuke.github.GHRepositoryStatistics.CommitActivity;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import io.ssafy.p.i3a110.dto.CommitInfoDto;
import io.ssafy.p.i3a110.dto.RepositoryInfoDto;

public class GitHubRestApiHelper {
	
	private GitHub github;
	private GHPerson person;

	// AccessToken으로 객체 생성
	public GitHubRestApiHelper(String accessToken) {
		try {
			this.github = new GitHubBuilder().withOAuthToken(accessToken).build();
			this.person = this.github.getMyself();
			System.out.println("Authentication Success");
		} catch (IOException e) {
			System.out.println("Authentication Failed");
			e.printStackTrace();
		}
	}
	
	// 사용자의 모든 Repository에 대해서 필요한 정보만 객체로 생성해서 List로 Return
	public ArrayList<RepositoryInfoDto> getAllRepositoryInfo(){
		ArrayList<RepositoryInfoDto> list = new ArrayList<RepositoryInfoDto>();
		try {
			List<GHRepository> repos = person.listRepositories().toList();
			for(GHRepository repo : repos) {
				RepositoryInfoDto ghrepo = new RepositoryInfoDto(repo.getName(), repo.getOwnerName(), repo.getHtmlUrl().toString(), repo.isPrivate());
				System.out.println(ghrepo);
				list.add(ghrepo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 날짜(Date) ,커밋수 형태로 리턴(오늘 날짜 포함 1년) 
	public HashMap<Date, Integer> getCommitInfo(String repoName) {
		HashMap<Date, Integer> cal = new HashMap<Date, Integer>();
		try {
			this.github.checkApiUrlValidity();
			GHRepository repo = this.person.getRepository(repoName);
			GHRepositoryStatistics stat = repo.getStatistics();
			List<CommitActivity> weekActivityList = stat.getCommitActivity().toList();
			for (int i = 0; i < weekActivityList.size(); i++) {
				CommitActivity weekActivity = weekActivityList.get(i);
				List<Integer> dayCnt = weekActivity.getDays();
				Long weekStart = weekActivityList.get(i).getWeek();
				for (int j = 0; j < dayCnt.size(); j++) {
					Date date = new Date(weekStart * 1000);
					cal.put(date, dayCnt.get(j));
					weekStart += 86400L;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cal;
	}
	
	// 사용자가 현재 등록한 Projects에 관한 Commit 수 합산
	public HashMap<Date, Integer> getAllCommitInfo(List<String> projectNames) {
		HashMap<Date, Integer> cal = new HashMap<Date, Integer>();
		List<Integer> sum = new ArrayList<Integer>();
		try {
			this.github.checkApiUrlValidity();
			for (String name : projectNames) {
				GHRepository repo = this.person.getRepository(name);
				GHRepositoryStatistics stat = repo.getStatistics();
				List<CommitActivity> weekActivityList = stat.getCommitActivity().toList();
				for (int i = 0; i < weekActivityList.size(); i++) {
					CommitActivity weekActivity = weekActivityList.get(i);
					List<Integer> dayCnt = weekActivity.getDays();
					Long weekStart = weekActivityList.get(i).getWeek();
					for (int j = 0; j < dayCnt.size(); j++) {
						Date date = new Date(weekStart * 1000);
						if(cal.containsKey(date)) {
							cal.put(date, cal.get(date) + dayCnt.get(j));
						}else {
							cal.put(date, dayCnt.get(j));
						}
						weekStart += 86400L;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cal;
	}
	
	public List<CommitInfoDto> getCommitListByDate(String repoName, Date date){
		return getCommitListByPeriod(repoName, date, date);
	}
	
	// 날짜(Date type) 입력 
	public List<CommitInfoDto> getCommitListByPeriod(String repoName, Date sDate, Date eDate){
		List<CommitInfoDto> list = new ArrayList<CommitInfoDto>();
		try {
			this.github.checkApiUrlValidity();
			GHRepository repo = person.getRepository(repoName);
			GHCommitQueryBuilder commitqb = repo.queryCommits();
			commitqb = commitqb.since(sDate);
			commitqb = commitqb.until(eDate);
			List<GHCommit> commits = commitqb.list().toList();
			for(GHCommit commit : commits) {
				String author = commit.getAuthor().getName();
				String sha1 = commit.getSHA1();
				Date date = commit.getCommitDate();
				String msg = commit.getCommitShortInfo().getMessage();
				list.add(new CommitInfoDto(author, sha1, date, msg));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}