//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.comment.dao.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class Shop {

    private Long id;

    private Byte status;

    private Long deposit;

    private Long depositThreshold;

    private String name;
}