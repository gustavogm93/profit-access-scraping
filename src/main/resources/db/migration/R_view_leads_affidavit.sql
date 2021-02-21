CREATE OR REPLACE VIEW view_leads_affidavit AS
Select * from view_leads where max_reach_level = 4 ;

