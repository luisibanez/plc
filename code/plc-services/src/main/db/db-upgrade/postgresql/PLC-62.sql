alter table patient_account add column survey_taken bool;
update patient_account set survey_taken = false;
alter table patient_account alter column survey_taken set not null;