package edu.whu.demo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Supplier {
    @TableId(value="id",type= IdType.AUTO)
    int id;
    String name;
    String address;

    
}
