package io.ssafy.p.i3a110.apihelper;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	
	public GitHubRestApiHelper() {}

	// AccessToken으로 객체 생성
	public GitHubRestApiHelper(String accessToken) {
		try {
			this.github = new GitHubBuilder().withOAuthToken(accessToken).build();
			this.person = this.github.getMyself();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//OAuth 토큰값과 ID 동일한지 확인
	public boolean checkOauth(String gitId, String accessToken) {
		try {
			GitHub github = new GitHubBuilder().withOAuthToken(accessToken).build();
			GHPerson person = github.getMyself();
			String id = person.getLogin();
			if(id.equals(gitId)) return true;
			else return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void getAuthenticationByOauth(String accessToken) {
		try {
			this.github = new GitHubBuilder().withOAuthToken(accessToken).build();
			this.person = this.github.getMyself();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 사용자의 모든 Repository에 대해서 필요한 정보만 객체로 생성해서 List로 Return
	public ArrayList<RepositoryInfoDto> getAllRepositoryInfo(){
		ArrayList<RepositoryInfoDto> list = new ArrayList<RepositoryInfoDto>();
		try {
			List<GHRepository> repos = person.listRepositories().toList();
			for(GHRepository repo : repos) {
				String name = repo.getName();
				String ownerName = repo.getOwnerName();
				String url = repo.getHtmlUrl().toString();
				boolean isPrivate = repo.isPrivate();
				List<String> languages = new ArrayList<String>();
				
				Map<String, Long> map = repo.listLanguages();
				Iterator<String> keys = map.keySet().iterator();
				while(keys.hasNext()) {
					languages.add(keys.next());
				}
				
				RepositoryInfoDto ghrepo = new RepositoryInfoDto(name,ownerName,url,isPrivate, languages);
				list.add(ghrepo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 날짜(Date) ,커밋수 형태로 리턴(오늘 날짜 포함 1년) 
	public HashMap<Date, Integer> getCommitCnt(String repoName) {
		HashMap<Date, Integer> cal = new HashMap<Date, Integer>();
		try {
			this.github.checkApiUrlValidity();
			Map<String, GHRepository> map = this.person.getRepositories();
			if(!map.containsKey(repoName)) {
				cal.put(Calendar.getInstance().getTime(), -1);
				return cal;
			}
			GHRepository repo = map.get(repoName);
			GHRepositoryStatistics stat = repo.getStatistics();
			List<CommitActivity> weekActivityList = stat.getCommitActivity().toList();
			for (int i = 0; i < weekActivityList.size(); i++) {
				CommitActivity weekActivity = weekActivityList.get(i);
				List<Integer> dayCnt = weekActivity.getDays();
				Long weekStart = weekActivityList.get(i).getWeek();
				for (int j = 0; j < dayCnt.size(); j++) {
					cal.put(new Date(weekStart * 1000), dayCnt.get(j));
					weekStart += 86400L;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cal;
	}
	
	// 사용자가 현재 등록한 Projects에 관한 Commit 수 합산
	public HashMap<Date, Integer> getAllCommitCnt(List<String> projectNames) {
		HashMap<Date, Integer> cal = new HashMap<Date, Integer>();
		try {
			this.github.checkApiUrlValidity();
			for (String name : projectNames) {
				Map<String, GHRepository> map = this.person.getRepositories();
				if(!map.containsKey(name)) {
					cal.put(Calendar.getInstance().getTime(), -1);
					continue;
				}
				GHRepository repo = map.get(name);
				
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
	
	public List<CommitInfoDto> getCommitInfoListByDate(String repoName, String date){
		return getCommitInfoListByPeriod(repoName, date, date);
	}
	
	// 날짜(Date type) 입력 
	public List<CommitInfoDto> getCommitInfoListByPeriod(String repoName, String sDate, String eDate){
		List<CommitInfoDto> list = new ArrayList<CommitInfoDto>();
		try {
			this.github.checkApiUrlValidity();
			GHRepository repo = person.getRepository(repoName);
			GHCommitQueryBuilder commitqb = repo.queryCommits();
			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
			
			if(sDate!=null && !sDate.equals("")) commitqb = commitqb.since(form.parse(sDate).getTime());
			if(eDate!=null && !eDate.equals("")) commitqb = commitqb.until(form.parse(eDate).getTime()+86399999);
			List<GHCommit> commits = commitqb.list().toList();
			for(GHCommit commit : commits) {
				String author = commit.getCommitShortInfo().getAuthor().getName();
				String sha1 = commit.getSHA1();
				Date date = commit.getCommitDate();
				String msg = commit.getCommitShortInfo().getMessage();
				list.add(new CommitInfoDto(author, sha1, date, msg));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public HashMap<String, String> getOdocRate(List<String> projectNames) {
		HashMap<String, String> output = new HashMap<String, String>();
		try {
			this.github.checkApiUrlValidity();
			boolean[] days = new boolean[84];
			int doCommitDays = 0;
			for (String name : projectNames) {
				Map<String, GHRepository> map = this.person.getRepositories();
				GHRepository repo = map.get(name);
				GHRepositoryStatistics stat = repo.getStatistics();
				List<CommitActivity> weekActivityList = stat.getCommitActivity().toList();
				for (int i = 39, idx = 0; i < 51; i++) {
					CommitActivity weekActivity = weekActivityList.get(i);
					List<Integer> dayCnt = weekActivity.getDays();
					for (int j = 0; j < dayCnt.size(); j++,idx++) {	
						if(dayCnt.get(j)>1 && !days[idx]) {
							days[idx] = true;
							doCommitDays++;
						}
					}
				}
			}
			output.put("days", String.format("%d/%d", doCommitDays, 84));
			output.put("rate", String.format("%.2f", (double)doCommitDays/84*100));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
}