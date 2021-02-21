CREATE OR REPLACE VIEW view_leads_magic_link AS
Select * from view_leads where  max_reach_level = 1;

