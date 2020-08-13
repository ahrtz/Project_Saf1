package io.ssafy.p.i3a110.apihelper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommitQueryBuilder;
import org.kohsuke.github.GHFileNotFoundException;
import org.kohsuke.github.GHPerson;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositoryStatistics;
import org.kohsuke.github.GHRepositoryStatistics.CommitActivity;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.HttpException;

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
	
	public boolean checkAuth() {
		GitHubBuilder builder = new GitHubBuilder();
		try {
			this.github = builder.build();
			github.checkAuth("", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	//OAuth 토큰값과 ID 동일한지 확인
	public boolean checkOauth(String gitId, String accessToken) {
		try {
			GitHub github = new GitHubBuilder().withOAuthToken(accessToken).build();
			GHPerson person = github.getMyself();
			String id = person.getLogin();
			if(id.equals(gitId)) return true;
			else return false;
		} catch(HttpException e) {
			System.out.println("GitID : " + gitId + " / AccesToken : " + accessToken +" / Error : "+ e.getMessage());
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
				String repoId = String.valueOf(repo.getId());
				List<String> languages = new ArrayList<String>();
				
				Map<String, Long> map = repo.listLanguages();
				Iterator<String> keys = map.keySet().iterator();
				while(keys.hasNext()) {
					languages.add(keys.next());
				}
				
				RepositoryInfoDto ghrepo = new RepositoryInfoDto(name,ownerName,url,isPrivate, languages, repoId);
				list.add(ghrepo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 날짜(Date) ,커밋수 형태로 리턴(오늘 날짜 포함 1년) 
	public HashMap<Date, Integer> getCommitCnt(String repoId) {
		HashMap<Date, Integer> cal = new HashMap<Date, Integer>();
		try {
			this.github.checkApiUrlValidity();
			
			GHRepository repo = this.github.getRepositoryById(repoId);
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
			return cal;
		} catch(GHFileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		cal.put(Calendar.getInstance().getTime(), -1);
		return cal;
	}
	
	// 사용자가 현재 등록한 Projects에 관한 Commit 수 합산
	public HashMap<Date, Integer> getAllCommitCnt(List<String> repoIds) {
		HashMap<Date, Integer> cal = new HashMap<Date, Integer>();
		try {
			this.github.checkApiUrlValidity();
			for(String repoId : repoIds) {
				GHRepository repo = null;
				try {
					repo = this.github.getRepositoryById(repoId);
				} catch(GHFileNotFoundException e) {
					System.out.println(e);
					continue;
				}
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
	
	public List<CommitInfoDto> getCommitInfoListByDate(String repoId, String date){
		return getCommitInfoListByPeriod(repoId, date, date);
	}
	
	// 날짜(Date type) 입력 
	public List<CommitInfoDto> getCommitInfoListByPeriod(String repoId, String sDate, String eDate){
		List<CommitInfoDto> list = new ArrayList<CommitInfoDto>();
		try {
			this.github.checkApiUrlValidity();
			GHRepository repo = this.github.getRepositoryById(repoId);
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
				String url = commit.getHtmlUrl().toString();
				list.add(new CommitInfoDto(author, sha1, date, msg, url));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public HashMap<String, String> getOdocRate(List<String> repoIds) {
		HashMap<String, String> output = new HashMap<String, String>();
		try {
			this.github.checkApiUrlValidity();
			boolean[] days = new boolean[84];
			int doCommitDays = 0;
			for (String repoId : repoIds) {
				GHRepository repo = null;
				try {
					repo = this.github.getRepositoryById(repoId);
				} catch(GHFileNotFoundException e) {
					System.out.println(e);
					continue;
				}
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
	
	//////////////////////////////////NEW//////////////////////////////////////
	
	public Map<Date, Integer> getAllCommitCnt(List<String> repoIds, String gitid, Date sDate, Date eDate) {
		eDate = cutTime(eDate);
		sDate = cutTime(sDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(eDate);
		calendar.add(Calendar.DATE, 1);
		eDate = new Date(calendar.getTimeInMillis());
		Map<Date, Integer> output = getDateMap(sDate, eDate);
		try {
			this.github.checkApiUrlValidity();
			for (String repoId : repoIds) {
				GHRepository repo = null;
				try {
					repo = this.github.getRepositoryById(repoId);
				} catch(GHFileNotFoundException e) {
					System.out.println(e);
					continue;
				}
				GHCommitQueryBuilder commitqb = repo.queryCommits();
		    	
				commitqb.since(sDate);
				commitqb.until(eDate);
				List<GHCommit> commits = commitqb.list().toList();
				for(GHCommit commit : commits) {
					String author = commit.getCommitShortInfo().getAuthor().getName();
					if(!author.equals(gitid)) continue;
					Date date = cutTime(commit.getCommitDate());
					output.put(date, output.get(date)+1);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public Map<String, String> getOdocRate(List<String> repoIds, String gitid) {
		HashMap<String, String> output = new HashMap<String, String>();
		
		Calendar calendar = Calendar.getInstance();
		Date eDate = new Date();
		calendar.setTime(eDate);
		calendar.add(Calendar.MONTH, -3);
		Date sDate = new Date(calendar.getTimeInMillis());
		Map<Date, Integer> map = getAllCommitCnt(repoIds, gitid, sDate, eDate);
    	int commitDays = 0;
    	int days = map.size();
    	
    	Iterator<Date> keys = map.keySet().iterator();
    	while(keys.hasNext()) {
    		Date key = keys.next();
    		if(map.get(key)>0) commitDays++;
    	}
		output.put("days", String.format("%d/%d", commitDays, days));
		output.put("rate", String.format("%.2f", (double)commitDays/days*100));
    	
		return output;
	}
	
	private Date cutTime(Date x) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(x);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	return new Date(calendar.getTimeInMillis());
	}
	
	private Map<Date, Integer> getDateMap(Date sDate, Date eDate){
		Map<Date, Integer> map = new TreeMap<Date, Integer>();
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(eDate.getTime()-sDate.getTime());
    	int days = calendar.get(Calendar.DAY_OF_YEAR);
    	calendar.setTime(sDate);
    	for (int i = 0; i < days-2; i++) {
			map.put(new Date(calendar.getTimeInMillis()), 0);
			calendar.add(Calendar.DATE, 1);
		}
		return map;
	}
}