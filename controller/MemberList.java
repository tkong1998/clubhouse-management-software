package controller;

import java.io.*;
import java.util.*;

public class MemberList {
    private ArrayList<Member> memberList;

    public MemberList() {
        this.memberList = new ArrayList<Member>();
        final String PATH = "data/member.csv";
        File file = new File(PATH);

        // if file don't exist, then create the file and return
        if (!file.exists()) {
            String header = "member_id,name,address,email,phone";
            try (FileOutputStream outputFile = new FileOutputStream(PATH)) {
                outputFile.write(header.getBytes());
                outputFile.flush();
                outputFile.close();
            } catch (Exception exception) {
                exception.getStackTrace();
            }
            return;
        }

        // load the member list
        try (Scanner input = new Scanner(file)) {
            String line, lineData[];
            for (int i = 0; input.hasNextLine(); i++) {
                line = input.nextLine();
                // skip the first line (header)
                if (i == 0) {
                    continue;
                }
                // skip the empty line
                if (line.trim().isEmpty()) {
                    continue;
                }
                lineData = line.split(",");
                String memberID = lineData[0];
                String name = lineData[1];
                String address = lineData[2];
                String email = lineData[3];
                String phone = lineData[4];

                Member member = new Member(name, email, memberID, address, phone);
                addMember(member);
            }
        } catch (Exception exception) {
            exception.getStackTrace();
        }
    }

    public ArrayList<Member> getMemberList(){
        return this.memberList;
    }

    public Member findMember(String memberID){
        for (Member member: memberList){
            
            if (member.getMemberID().equals(memberID)){
                return member;
            }
        }
        return null;
    }

    public void addMember(Member member){
        this.memberList.add(member);
    }
}