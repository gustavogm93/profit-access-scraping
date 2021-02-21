CREATE OR REPLACE VIEW view_onboarding_global_cases AS
SELECT max_reached_level as sort, state,id ,COUNT(1) as count from
	(
		SELECT r.device_id,
		count(1) AS requests,
		(
			SELECT MAX(CASE onboarding_state
				WHEN 'MAGIC_LINK_SENT' THEN 1
				WHEN 'EMAIL_VALIDATED' THEN 2
				WHEN 'IDENTITY_VERIFIED' THEN 3
				WHEN 'AFFIDAVIT_FILLED_OUT' THEN 4
				WHEN 'TERMS_AND_CONDITIONS_ACCEPTED' THEN 5
				ELSE 6  END)
			FROM people_hub_int.sign_up_request AS r2 WHERE r2.device_id = r.device_id
		) AS max_reached_level, CASE onboarding_state
				WHEN 'MAGIC_LINK_SENT' THEN 'Magic Link enviado'
				WHEN 'EMAIL_VALIDATED' THEN 'Validacion Email'
				WHEN 'IDENTITY_VERIFIED' THEN 'Validacion identidad'
				WHEN 'AFFIDAVIT_FILLED_OUT' THEN 'Declaracion Jurada'
				WHEN 'TERMS_AND_CONDITIONS_ACCEPTED' THEN 'Terminos y condiciones'
				ELSE 'Compleado' END AS state, onboarding_state AS id
		FROM people_hub_int.sign_up_request AS r
		#where r.created_at >= '20200811'
		GROUP BY r.device_id
	) as tabla
GROUP BY max_reached_level;
