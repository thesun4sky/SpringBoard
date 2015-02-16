package com.choi.board.util;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

import com.choi.board.model.Article;

@Component
public class CalcSeq {
	/*
	 * 아마 게시판에서 페이징 보다 어려운 기능은 리플기능인것 같다. 
	 * 원본 글에 대한 답변 글이 달려 있을경우 원본보다 아래 달려 있어양 한다.
	 * 페이징시 리플 글에대한 처리는 리스트를 가져올때 이미 세팅 해놓았기 때문에 여기서는 해당 데이터에 대한 값을
	 * 입력을 잘 해주면 된다.
	 * */
	public String getSearchMinSeqNum(Article parent){
		
		String parentSeqNum = parent.getSequenceNumber();
		DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
		long parentSeqLongValue = Long.parseLong(parentSeqNum);
		long searchMinLongValue = 0;
		switch (parent.getLevel()) { //여기서 레벨이란 ?? 원본 글에 대한 답변일 경우 0 리플의 리플일 경우 1 리플의 리플의 리플의 경우2 그 이상은 막아 주는게 좋을듯 싶습니다.
		case 0:
			searchMinLongValue = parentSeqLongValue / 1000000L * 1000000L;
			break;
		case 1:
			searchMinLongValue = parentSeqLongValue / 10000L * 10000L;
			break;
		case 2:
			searchMinLongValue = parentSeqLongValue / 100L * 100L;
			break;
		}
		return decimalFormat.format(searchMinLongValue);
	}

	public String getSequenceNumber(Article parent,String lastChildSeq) throws Exception{
		
		long parentSeqLong = Long.parseLong(parent.getSequenceNumber());
		int parentLevel = parent.getLevel();
		
		long decUnit = 0;
		if (parentLevel == 0) {
			decUnit = 10000L;
		} else if (parentLevel == 1) {
			decUnit = 100L;
		} else if (parentLevel == 2) {
			decUnit = 1L;
		}

		String sequenceNumber = null;

		DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
		if (lastChildSeq == null) { // 답변 글이 없음
			sequenceNumber = decimalFormat.format(parentSeqLong - decUnit);
		} else { // 답변 글이 있음
			// 마지막 답변 글인지 확인
			String orderOfLastChildSeq = null;
			if (parentLevel == 0) {
				orderOfLastChildSeq = lastChildSeq.substring(10, 12);
				sequenceNumber = lastChildSeq.substring(0, 12) + "9999";
			} else if (parentLevel == 1) {
				orderOfLastChildSeq = lastChildSeq.substring(12, 14);
				sequenceNumber = lastChildSeq.substring(0, 14) + "99";
			} else if (parentLevel == 2) {
				orderOfLastChildSeq = lastChildSeq.substring(14, 16);
				sequenceNumber = lastChildSeq;
			}
			if (orderOfLastChildSeq.equals("00")) {
				throw new Exception("마지막 자식 글이 이미 존재합니다:" + lastChildSeq);
			}
			long seq = Long.parseLong(sequenceNumber) - decUnit;
			sequenceNumber = decimalFormat.format(seq);
		}
		return sequenceNumber;
	}
}
