/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pnj.codeDetails;

import pnj.codes.CodesDTO;
import pnj.status.StatusDTO;

/**
 *
 * @author Admin
 */
public class CodeDetailDTO {

    private CodesDTO code;
    private String userID;
    private StatusDTO status;

    public CodeDetailDTO(CodesDTO code, String userID, StatusDTO status) {
        this.code = code;
        this.userID = userID;
        this.status = status;
    }

    @Override
    public String toString() {
        return "CodeDetailDTO{" + "code=" + code + ", userID=" + userID + ", status=" + status + '}';
    }

    public CodesDTO getCode() {
        return code;
    }

    public void setCode(CodesDTO code) {
        this.code = code;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

}
