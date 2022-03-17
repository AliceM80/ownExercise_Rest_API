package de.oette.lecture.A04.controller;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ValueDto {
  @Min(value=1)
  @Max(value=100)
  public Long value;

  @Valid  //wenn nur diese Annotation allein genutzt wird, is null ein gültiger Wert, soll das nicht so sein @NotNull benötigt
  //@NotNull
  private SubValueDto subValueDto;


 public SubValueDto getSubValueDto() {  //mit @Valid wird auch auf dieser Ebene validiert
return subValueDto;
 }

 public void setSubValueDto(SubValueDto subValueDto){
   this.subValueDto = subValueDto;
 }
}
