package com.aiden.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.aiden.board.mapper.ComponentMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SchedulerService {
	
	@Autowired
	ComponentMapper componentMapper;
	
	//매일 오후 12시
	@Scheduled(cron = "0 0 12 * * *") 
	public void initializeVoteState() {
		componentMapper.initializeVoteState();
	}
}
