CREATE VIEW view_leads AS
SELECT 
person.id,
person.type,
count(1) as intentos,
person.name,
person.last_name,
person.gender,
person.cuil,
contact.email,
document.number,
document.procedure,
document.expiration_date,
sign_up_request.state,
sign_up_request.device_id,
sign_up_request.face_url,
sign_up_request.id_front_url,
sign_up_request.id_back_url,
sign_up_request.created_at,
sign_up_request.renaper_verify_photo,
sign_up_request.crosscheck_document_data,
sign_up_request.renaper_customer_document_data,
sign_up_request.facephi_customer_document_data,
sign_up_request.facephi_verify_face_match,
SUM(sign_up_request.facephi_timeout_count),
SUM(sign_up_request.facephi_wrong_face_match_count),
SUM(sign_up_request.renaper_wrong_face_match_count),
sign_up_request.onboarding_state,
MAX(CASE onboarding_state
				WHEN 'MAGIC_LINK_SENT' THEN 1
				WHEN 'EMAIL_VALIDATED' THEN 2
				WHEN 'IDENTITY_VERIFIED' THEN 3
				WHEN 'AFFIDAVIT_FILLED_OUT' THEN 4
				WHEN 'TERMS_AND_CONDITIONS_ACCEPTED' THEN 5
                WHEN 'PASSCODE_CREATED' THEN 6
				ELSE 10  END) max_reach_level,
SUM(sign_up_request.expired_dni),
SUM(sign_up_request.demise_notice),
SUM(sign_up_request.invalid_crosscheck_data),
SUM(sign_up_request.invalid_dni_gender),
MAX(person.created_at)
FROM tmp_people_hub.person
LEFT JOIN tmp_people_hub.sign_up_request ON sign_up_request.id = person.sign_up_request_id
LEFT JOIN tmp_people_hub.document ON document.id = person.document_id
LEFT JOIN tmp_people_hub.contact ON contact.id = person.contact_id
#WHERE sign_up_request.onboarding_state="EMAIL_VALIDATED"
group by sign_up_request.device_id
having max_reach_level < 10
order by intentos desc;



