package ke.co.hakicheck.data.repository

import ke.co.hakicheck.domain.model.*
import java.util.UUID

object OfflineSeedData {

    val sampleClaims = listOf(
        "Kenyan citizens must pay Ksh 500 fee to register as a voter in 2027.",
        "IEBC announced electronic voting replaces ballot papers completely.",
        "Any registered Kenyan citizen aged 18+ can run for County Assembly without a degree.",
        "Voting day is declared a mandatory public holiday in Kenya."
    )

    val verifiedCorpus: List<ClaimVerification> = listOf(
        ClaimVerification(
            id = "claim_election_date_000",
            claimText = "What date do elections take place",
            verdict = VerdictType.CIVIC_INFO,
            summary = "Under Article 101(1) and Article 136(2)(a) of the Constitution of Kenya 2010, General Elections are held on the second Tuesday in August in every fifth year. The next Kenyan General Election is scheduled for Tuesday, August 10, 2027.",
            groundedContext = "Articles 101(1), 136(2)(a), 177(1)(a), and 180(1) constitutionally fix polling day to the second Tuesday in August every fifth year.",
            confidenceScore = 99,
            sourceCategory = "ELECTIONS",
            evidenceSources = listOf(
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 101(1)",
                    legalCitation = "CoK 2010, Art. 101(1)",
                    extractSnippet = "A general election of members of Parliament shall be held on the second Tuesday in August in every fifth year.",
                    documentType = "CONSTITUTION"
                ),
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 136(2)(a)",
                    legalCitation = "CoK 2010, Art. 136(2)(a)",
                    extractSnippet = "An election of the President shall be held on the same day as a general election of Members of Parliament, being the second Tuesday in August in every fifth year.",
                    documentType = "CONSTITUTION"
                )
            )
        ),
        ClaimVerification(
            id = "claim_presidential_age_000",
            claimText = "How old must I be to vie for President",
            verdict = VerdictType.CIVIC_INFO,
            summary = "Under Article 137(1) of the Constitution of Kenya 2010, a presidential candidate must be a citizen by birth and qualified to stand for election as an MP (meaning an adult citizen aged 18+ who is a registered voter and holds a recognized degree). The 2010 Constitution abolished the old 35-year age floor.",
            groundedContext = "Article 137(1)(b) ties presidential qualifications to Article 99 MP qualifications (adult citizen 18+).",
            confidenceScore = 96,
            sourceCategory = "ELECTIONS",
            evidenceSources = listOf(
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 137(1)",
                    legalCitation = "CoK 2010, Art. 137(1)",
                    extractSnippet = "A person is qualified for nomination as a presidential candidate if the person is a citizen by birth, is qualified to stand for election as a member of Parliament, and is nominated by a party or is an independent candidate.",
                    documentType = "CONSTITUTION"
                ),
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 99(1)",
                    legalCitation = "CoK 2010, Art. 99(1)(a)",
                    extractSnippet = "A person is eligible for election as a member of Parliament if the person is registered as a voter and satisfies educational, moral and ethical requirements.",
                    documentType = "CONSTITUTION"
                )
            )
        ),
        ClaimVerification(
            id = "claim_nairobi_governor_000",
            claimText = "Who is the current governor of Nairobi",
            verdict = VerdictType.CIVIC_INFO,
            summary = "The current Governor of Nairobi County is Johnson Arthur Sakaja, who was elected in the August 2022 General Election and sworn into office pursuant to Article 180 of the Constitution of Kenya 2010 and the County Governments Act.",
            groundedContext = "Article 180 governs direct popular election of County Governors every five years.",
            confidenceScore = 98,
            sourceCategory = "DEVOLUTION",
            evidenceSources = listOf(
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 180(1)",
                    legalCitation = "CoK 2010, Art. 180(1)",
                    extractSnippet = "The county governor shall be directly elected by the voters registered in the county, on the same day as a general election of Members of Parliament, being the second Tuesday in August in every fifth year.",
                    documentType = "CONSTITUTION"
                ),
                EvidenceSource(
                    title = "County Governments Act No. 17 of 2012 — Section 30",
                    legalCitation = "Cap. 265 Laws of Kenya, Sec. 30",
                    extractSnippet = "Functions and powers of the County Governor: Diligently execute functions conferred by the Constitution and provide leadership in county governance.",
                    documentType = "STATUTE"
                )
            )
        ),
        ClaimVerification(
            id = "claim_raila_president_000",
            claimText = "Raila Odinga is the current president",
            verdict = VerdictType.VERIFIED_FALSE,
            summary = "Raila Odinga is not the current President of Kenya. William Samoei Ruto was declared the winner of the August 2022 Presidential Election by the IEBC and was officially sworn in as the 5th President of Kenya on September 13, 2022 under Article 141 of the Constitution.",
            groundedContext = "Presidential election results upheld by the Supreme Court of Kenya in Presidential Petition No. E001 of 2022.",
            confidenceScore = 99,
            sourceCategory = "ELECTIONS",
            evidenceSources = listOf(
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 141(1)",
                    legalCitation = "CoK 2010, Art. 141(1)",
                    extractSnippet = "The swearing in of the President-elect shall be in public before the Chief Justice, or, in the absence of the Chief Justice, the Deputy Chief Justice.",
                    documentType = "CONSTITUTION"
                ),
                EvidenceSource(
                    title = "Supreme Court of Kenya — Presidential Petition No. E001 of 2022",
                    legalCitation = "Raila Odinga & Another v IEBC & Others [2022] eKLR",
                    extractSnippet = "The Supreme Court unanimously upheld the declaration of William Samoei Ruto as President-elect under Article 138(10) of the Constitution.",
                    documentType = "JUDGMENT"
                )
            )
        ),
        ClaimVerification(
            id = "claim_woman_rep_000",
            claimText = "Who can run for the woman rep position",
            verdict = VerdictType.VERIFIED_TRUE,
            summary = "Under Article 97(1)(b) and Article 99 of the Constitution of Kenya 2010, the County Woman Representative seat is contested exclusively by female Kenyan citizens who are registered adult voters, satisfy Chapter Six integrity requirements, and are nominated by a political party or run as independent candidates.",
            groundedContext = "Article 97(1)(b) creates 47 single-member county constituencies reserved for women members of the National Assembly.",
            confidenceScore = 97,
            sourceCategory = "ELECTIONS",
            evidenceSources = listOf(
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 97(1)(b)",
                    legalCitation = "CoK 2010, Art. 97(1)(b)",
                    extractSnippet = "The National Assembly consists of forty-seven women, each elected by the registered voters of the counties, each county constituting a single member constituency.",
                    documentType = "CONSTITUTION"
                ),
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 99",
                    legalCitation = "CoK 2010, Art. 99",
                    extractSnippet = "Unless disqualified, a person is eligible for election as a member of Parliament if registered as a voter, satisfies educational and ethical standards, and is nominated by a party or is an independent candidate.",
                    documentType = "CONSTITUTION"
                ),
                EvidenceSource(
                    title = "IEBC Voter Education Training Manual — Elective Positions",
                    legalCitation = "IEBC Manual, Page 74 & 83",
                    extractSnippet = "The elective positions in Kenya are: Presidency, Governorship, Senatorial, National Assembly, County Woman Member to the National Assembly, and County Assembly Ward Representative.",
                    documentType = "REGULATION"
                )
            )
        ),
        ClaimVerification(
            id = "claim_voting_age_000",
            claimText = "I can vote under the age of 18",
            verdict = VerdictType.VERIFIED_FALSE,
            summary = "Under Article 38(3) and Article 83(1)(a) of the Constitution of Kenya 2010, only an adult citizen has the right to be registered and vote. Article 260 defines an adult as an individual who has attained the age of 18 years. Minors under 18 cannot vote in Kenyan elections.",
            groundedContext = "CoK 2010 Article 83(1)(a) explicitly mandates that a person qualifies for registration as a voter only if the person is an adult citizen (18+).",
            confidenceScore = 99,
            sourceCategory = "ELECTIONS",
            evidenceSources = listOf(
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 83(1)(a)",
                    legalCitation = "CoK 2010, Art. 83(1)(a)",
                    extractSnippet = "A person qualifies for registration as a voter at elections or referenda if the person is an adult citizen, is not declared to be of unsound mind, and has not been convicted of an election offence.",
                    documentType = "CONSTITUTION"
                ),
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 260",
                    legalCitation = "CoK 2010, Art. 260 (Interpretation)",
                    extractSnippet = "'adult' means an individual who has attained the age of eighteen years; 'youth' means the collectivity of all individuals who have attained the age of eighteen years, but have not attained thirty-five years.",
                    documentType = "CONSTITUTION"
                ),
                EvidenceSource(
                    title = "Elections Act No. 24 of 2011 — Section 5(1)",
                    legalCitation = "Cap. 7 Laws of Kenya, Sec. 5(1)",
                    extractSnippet = "The Commission shall continuously register citizens who are eligible as voters at all gazetted registration centres upon production of a valid national identity card or Kenyan passport.",
                    documentType = "STATUTE"
                )
            )
        ),
        ClaimVerification(
            id = "claim_voter_fee_001",
            claimText = "Kenyan citizens must pay Ksh 500 fee to register as a voter in 2027.",
            verdict = VerdictType.VERIFIED_FALSE,
            summary = "Voter registration in Kenya is entirely free of charge. Under both the 2010 Constitution and statutory electoral guidance from the IEBC, no fee or administrative payment can be demanded from any Kenyan citizen to register as a voter.",
            groundedContext = "Article 38(3) guarantees free exercise of political rights. Section 5 of the Elections Act 2011 explicitly prohibits charging fees for continuous voter registration at constituency offices.",
            confidenceScore = 98,
            sourceCategory = "FEES",
            evidenceSources = listOf(
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 38(3)",
                    legalCitation = "CoK 2010, Art. 38(3)(a)",
                    extractSnippet = "Every adult citizen has the right, without unreasonable restrictions, to be registered as a voter; to vote by secret ballot in any election or referendum; and to be a candidate for public office.",
                    documentType = "CONSTITUTION"
                ),
                EvidenceSource(
                    title = "Elections Act No. 24 of 2011 — Section 5(1)",
                    legalCitation = "Cap. 7 Laws of Kenya, Sec. 5(1)",
                    extractSnippet = "Continuous registration of voters: The Commission shall continuously register citizens who are eligible as voters at all gazetted registration centres without imposing any fee.",
                    documentType = "STATUTE"
                )
            )
        ),
        ClaimVerification(
            id = "claim_electronic_voting_002",
            claimText = "IEBC announced electronic voting replaces ballot papers completely.",
            verdict = VerdictType.MISLEADING,
            summary = "Kenya uses an electronic system (KIEMS) exclusively for voter biometric identification, voter verification, and results transmission. Actual casting of ballots at polling stations is conducted using physical, marked paper ballots deposited in sealed ballot boxes.",
            groundedContext = "Section 44 of the Elections Act mandates an integrated electronic system for identification and transmission, while Section 10 confirms physical marked paper ballots remain the primary method of voting.",
            confidenceScore = 94,
            sourceCategory = "ELECTIONS",
            evidenceSources = listOf(
                EvidenceSource(
                    title = "Elections Act No. 24 of 2011 — Section 44(1)",
                    legalCitation = "Cap. 7 Laws of Kenya, Sec. 44(1)",
                    extractSnippet = "The Commission shall establish an integrated electronic electoral system that includes biometric voter registration, biometric voter identification, and electronic transmission of election results.",
                    documentType = "STATUTE"
                ),
                EvidenceSource(
                    title = "Supreme Court of Kenya — Presidential Petition No. 1 of 2017",
                    legalCitation = "[2017] eKLR, Paragraph 289",
                    extractSnippet = "Kenya's electoral architecture is hybrid: digital at the gate for biometric voter authentication and digital at exit for image transmission of Form 34A, but fundamentally manual and paper-based in mark and deposition.",
                    documentType = "JUDGMENT"
                )
            )
        ),
        ClaimVerification(
            id = "claim_mca_degree_003",
            claimText = "Any registered Kenyan citizen aged 18+ can run for County Assembly without a degree.",
            verdict = VerdictType.VERIFIED_TRUE,
            summary = "In October 2022, the High Court of Kenya declared Section 22(1)(b) of the Elections Act unconstitutional, ruling that requiring a university degree for Members of County Assembly (MCA) violates Article 38 of the Constitution.",
            groundedContext = "High Court Judgment in Petition No. E229 of 2022 (Victor Buoga v. IEBC) voided the degree requirement for County Assembly candidates.",
            confidenceScore = 96,
            sourceCategory = "BILL_OF_RIGHTS",
            evidenceSources = listOf(
                EvidenceSource(
                    title = "High Court of Kenya — Constitutional Petition No. E229 of 2022",
                    legalCitation = "Buoga v Independent Electoral and Boundaries Commission [2022] eKLR",
                    extractSnippet = "Section 22(1)(b) of the Elections Act requiring a degree qualification for candidates vying for Member of County Assembly is discriminatory, disproportionate, and unconstitutional.",
                    documentType = "JUDGMENT"
                ),
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 193(1)",
                    legalCitation = "CoK 2010, Art. 193(1)(a)",
                    extractSnippet = "Unless disqualified under clause (2), a person is eligible for election as a member of a county assembly if the person is a registered voter and satisfies any educational, moral and ethical requirements prescribed by this Constitution or an Act of Parliament.",
                    documentType = "CONSTITUTION"
                )
            )
        ),
        ClaimVerification(
            id = "claim_holiday_004",
            claimText = "Voting day is declared a mandatory public holiday in Kenya.",
            verdict = VerdictType.MOSTLY_TRUE,
            summary = "While the Constitution does not automatically make election day a holiday in permanent statute, the Ministry of Interior routinely gazettes general election polling days as national public holidays under the Public Holidays Act to allow all registered employees to vote.",
            groundedContext = "Gazetted under the Public Holidays Act (Cap 110) ahead of every general election cycle in accordance with Article 38 political franchise guarantees.",
            confidenceScore = 92,
            sourceCategory = "ELECTIONS",
            evidenceSources = listOf(
                EvidenceSource(
                    title = "Public Holidays Act — Section 3",
                    legalCitation = "Cap. 110 Laws of Kenya, Sec. 3",
                    extractSnippet = "The Cabinet Secretary may at any time by notice in the Gazette declare any day to be a public holiday either in addition to the days mentioned in the Schedule or in substitution for any of them.",
                    documentType = "STATUTE"
                )
            )
        ),
        ClaimVerification(
            id = "claim_maina_kiai_005",
            claimText = "Presidential election results announced at the polling station are final.",
            verdict = VerdictType.VERIFIED_TRUE,
            summary = "Under the landmark Maina Kiai case (2017) upheld by the Court of Appeal and Supreme Court, presidential results declared by the Presiding Officer at each polling station on Form 34A are final and cannot be altered, corrected, or varied by anyone, including the National Returning Officer.",
            groundedContext = "Independent Electoral and Boundaries Commission v Maina Kiai & 5 Others [2017] eKLR confirmed that the polling station is the primary locus of Kenyan democratic expression.",
            confidenceScore = 99,
            sourceCategory = "ELECTIONS",
            evidenceSources = listOf(
                EvidenceSource(
                    title = "Court of Appeal (Maina Kiai Case) — Civil Appeal No. 105 of 2017",
                    legalCitation = "IEBC v Maina Kiai & 5 Others [2017] eKLR",
                    extractSnippet = "The results declared by the Presiding Officer at the polling station are final and not subject to alteration by any person, including the Chairperson of the Commission, except by the Supreme Court in an election petition.",
                    documentType = "JUDGMENT"
                ),
                EvidenceSource(
                    title = "Constitution of Kenya 2010 — Article 86(a)",
                    legalCitation = "CoK 2010, Art. 86(a)",
                    extractSnippet = "At every election, the Independent Electoral and Boundaries Commission shall ensure that whatever voting method is used, the system is simple, accurate, verifiable, secure, accountable and transparent.",
                    documentType = "CONSTITUTION"
                )
            )
        )
    )

    val trendingClaims: List<TrendingClaim> = listOf(
        TrendingClaim(
            id = "trend_linda_mama",
            claimTitle = "Linda Mama maternity healthcare abolished under SHIF/SHA",
            viralContext = "Viral posts alleging that free maternal healthcare has been discontinued and mothers must pay Ksh 20,000 upfront.",
            verdict = VerdictType.MISLEADING,
            summary = "Linda Mama has not been abolished. Under Article 43(1)(a) of the Constitution and the Social Health Insurance Act 2023, maternal care is transitioned into the SHA Primary Healthcare Fund ensuring free deliveries at public facilities.",
            verification = ClaimVerification(
                id = "claim_linda_mama_001",
                claimText = "Linda Mama maternity healthcare has been abolished under SHIF/SHA.",
                verdict = VerdictType.MISLEADING,
                summary = "The Linda Mama free maternity program has not been scrapped. Under Article 43(1)(a) of the Constitution (Right to Health) and the Social Health Insurance Act, 2023, maternal care services have been transitioned from NHIF into the Social Health Authority (SHA) benefit package under the Primary Healthcare Fund, preserving free delivery and post-natal care at public health facilities.",
                groundedContext = "Article 43(1)(a) of the Constitution of Kenya guarantees reproductive health rights, and the Social Health Insurance (General) Regulations 2024 mandate free primary maternal services.",
                confidenceScore = 95,
                sourceCategory = "BILL_OF_RIGHTS",
                evidenceSources = listOf(
                    EvidenceSource(
                        title = "Constitution of Kenya 2010 — Article 43(1)(a)",
                        legalCitation = "CoK 2010, Art. 43(1)(a)",
                        extractSnippet = "Every person has the right to the highest attainable standard of health, which includes the right to health care services, including reproductive health care.",
                        documentType = "CONSTITUTION"
                    ),
                    EvidenceSource(
                        title = "Social Health Insurance Act No. 16 of 2023 — Section 20",
                        legalCitation = "Cap. 255A Laws of Kenya, Sec. 20",
                        extractSnippet = "The Primary Healthcare Fund shall cover essential outpatient, inpatient, and maternal healthcare services for all residents without requiring out-of-pocket payments at levels 1 to 4 public facilities.",
                        documentType = "STATUTE"
                    )
                )
            ),
            viralCount = 9840
        ),
        TrendingClaim(
            id = "trend_sifuna_protest",
            claimTitle = "Senator Edwin Sifuna sponsored a bill to ban public protests",
            viralContext = "Audio clip claiming Nairobi Senator Edwin Sifuna drafted a bill mandating 30 days police notice and banning city centre demonstrations.",
            verdict = VerdictType.VERIFIED_FALSE,
            summary = "Under Article 37 of the Constitution of Kenya 2010, the right to peaceably assemble and demonstrate is protected. Senator Sifuna has sponsored no such bill.",
            verification = ClaimVerification(
                id = "claim_sifuna_protest_002",
                claimText = "Senator Edwin Sifuna sponsored a bill to ban public protests and picketing.",
                verdict = VerdictType.VERIFIED_FALSE,
                summary = "Under Article 37 of the Constitution of Kenya 2010, every person has the protected right, peaceably and unarmed, to assemble, demonstrate, picket, and present petitions. Senator Edwin Sifuna has not authored or sponsored any legislative bill restricting public demonstrations or criminalizing picketing.",
                groundedContext = "CoK 2010 Article 37 guarantees the right to peaceful assembly without requiring discretionary executive or legislative prohibition.",
                confidenceScore = 99,
                sourceCategory = "BILL_OF_RIGHTS",
                evidenceSources = listOf(
                    EvidenceSource(
                        title = "Constitution of Kenya 2010 — Article 37",
                        legalCitation = "CoK 2010, Art. 37",
                        extractSnippet = "Every person has the right, peaceably and unarmed, to assemble, to demonstrate, to picket, and to present petitions to public authorities.",
                        documentType = "CONSTITUTION"
                    ),
                    EvidenceSource(
                        title = "High Court of Kenya — Petition No. 159 of 2018 (Police Notice vs Permission)",
                        legalCitation = "[2018] eKLR, Paragraph 112",
                        extractSnippet = "Citizens are only required to notify the police for security facilitation; the police have no statutory power to grant or deny constitutional permission for peaceful demonstrations.",
                        documentType = "JUDGMENT"
                    )
                )
            ),
            viralCount = 8120
        ),
        TrendingClaim(
            id = "trend_housing_levy",
            claimTitle = "Housing Levy applies only to formal payslips, exempting informal earners",
            viralContext = "Widely circulated infographics claiming market vendors, gig workers, and informal traders do not pay the 1.5% housing levy.",
            verdict = VerdictType.VERIFIED_FALSE,
            summary = "The Affordable Housing Act 2024 (Section 4) assesses 1.5% on all gross income, including informal and non-salaried earners, to satisfy constitutional tax equity.",
            verification = ClaimVerification(
                id = "claim_housing_levy_003",
                claimText = "Housing Levy applies only to formal payslips and exempts the informal sector.",
                verdict = VerdictType.VERIFIED_FALSE,
                summary = "Under Section 4 of the Affordable Housing Act, 2024, the Affordable Housing Levy of 1.5% is levied on the gross income of any person receiving income, covering both formal salaried employees and informal business or non-salaried income earners. The High Court previously ruled that excluding informal earners violated Article 201 of the Constitution.",
                groundedContext = "Affordable Housing Act 2024 enacted after High Court Petition No. E528 of 2023 mandated equal tax burdens under Article 201.",
                confidenceScore = 98,
                sourceCategory = "FEES",
                evidenceSources = listOf(
                    EvidenceSource(
                        title = "Affordable Housing Act No. 2 of 2024 — Section 4(1)",
                        legalCitation = "Act No. 2 of 2024, Sec. 4",
                        extractSnippet = "There is imposed a levy to be known as the Affordable Housing Levy payable by employees at 1.5% of gross salary matched by employers, and payable by any other person who derives gross income in Kenya at 1.5%.",
                        documentType = "STATUTE"
                    ),
                    EvidenceSource(
                        title = "Constitution of Kenya 2010 — Article 201(b)(i)",
                        legalCitation = "CoK 2010, Art. 201(b)(i)",
                        extractSnippet = "The burden of taxation shall be shared fairly among all citizens.",
                        documentType = "CONSTITUTION"
                    )
                )
            ),
            viralCount = 6730
        ),
        TrendingClaim(
            id = "trend_iebc_selection",
            claimTitle = "President can directly appoint IEBC Commissioners without a selection panel",
            viralContext = "Debates on social media claiming the Executive has unilateral authority to pick the new IEBC Chairman and Commissioners.",
            verdict = VerdictType.VERIFIED_FALSE,
            summary = "Under Article 88 and the IEBC (Amendment) Act 2024, appointments require an independent Selection Panel and National Assembly vetting.",
            verification = ClaimVerification(
                id = "claim_iebc_selection_004",
                claimText = "The President can directly appoint IEBC Commissioners without an independent selection panel.",
                verdict = VerdictType.VERIFIED_FALSE,
                summary = "Under Article 88 of the Constitution and the Independent Electoral and Boundaries Commission (Amendment) Act 2024, the President cannot directly appoint commissioners. Nominees must be competitively interviewed and shortlisted by an independent Selection Panel representing the Parliamentary Service Commission, the Law Society of Kenya (LSK), and religious bodies, and vetted by the National Assembly before formal appointment.",
                groundedContext = "Article 88 of the Constitution and the IEBC (Amendment) Act 2024 strictly mandate a multi-sector selection panel.",
                confidenceScore = 99,
                sourceCategory = "ELECTIONS",
                evidenceSources = listOf(
                    EvidenceSource(
                        title = "Constitution of Kenya 2010 — Article 88(2)",
                        legalCitation = "CoK 2010, Art. 88(2)",
                        extractSnippet = "The Commission consists of a chairperson and not more than six other members appointed in accordance with the Constitution and legislation.",
                        documentType = "CONSTITUTION"
                    ),
                    EvidenceSource(
                        title = "IEBC (Amendment) Act 2024 — First Schedule",
                        legalCitation = "Act No. 4 of 2024, First Schedule",
                        extractSnippet = "The Selection Panel shall invite applications, consider the qualifications, shortlist and interview applicants, and forward the names of the qualified persons to the President for nomination.",
                        documentType = "STATUTE"
                    )
                )
            ),
            viralCount = 5490
        ),
        TrendingClaim(
            id = "trend_term_limit",
            claimTitle = "Parliament can extend Presidential term limit to 7 years without a referendum",
            viralContext = "Claims circulating online that a parliamentary bill will extend the presidential tenure before the 2027 elections.",
            verdict = VerdictType.VERIFIED_FALSE,
            summary = "Under Articles 142(2) and 255(1)(f) of the Constitution, altering the presidential term limit requires a mandatory national referendum.",
            verification = ClaimVerification(
                id = "claim_term_limit_005",
                claimText = "Parliament can extend the presidential term from 5 to 7 years without a referendum.",
                verdict = VerdictType.VERIFIED_FALSE,
                summary = "Under Article 142(2) of the Constitution of Kenya 2010, the term of office of the President is strictly two five-year terms. Under Article 255(1)(f), any constitutional amendment affecting the term of office of the President cannot become law through Parliament alone; it mandates an affirmative national referendum approved by at least 20% of registered voters in at least 24 counties.",
                groundedContext = "CoK 2010 Article 255(1)(f) explicitly entrenches the presidential term of office within the referendum requirement.",
                confidenceScore = 99,
                sourceCategory = "ELECTIONS",
                evidenceSources = listOf(
                    EvidenceSource(
                        title = "Constitution of Kenya 2010 — Article 142(2)",
                        legalCitation = "CoK 2010, Art. 142(2)",
                        extractSnippet = "A person shall not hold office as President for more than two terms.",
                        documentType = "CONSTITUTION"
                    ),
                    EvidenceSource(
                        title = "Constitution of Kenya 2010 — Article 255(1)(f)",
                        legalCitation = "CoK 2010, Art. 255(1)(f)",
                        extractSnippet = "A proposed amendment to this Constitution shall be enacted in accordance with Article 256 or 257, and approved in a referendum by a majority of the citizens voting if the amendment relates to the term of office of the President.",
                        documentType = "CONSTITUTION"
                    )
                )
            ),
            viralCount = 7650
        ),
        TrendingClaim(
            id = "trend_voter_fee",
            claimTitle = "IEBC charging Ksh 500 voter registration fee",
            viralContext = "WhatsApp voice notes claiming new youth applicants must pay Ksh 500 at Huduma Centres to get a voter registration slip.",
            verdict = VerdictType.VERIFIED_FALSE,
            summary = "Voter registration in Kenya is guaranteed 100% free of charge under Article 38(3) and Section 5 of Elections Act.",
            verification = ClaimVerification(
                id = "claim_voter_fee_006",
                claimText = "Kenyan citizens must pay Ksh 500 fee to register as a voter.",
                verdict = VerdictType.VERIFIED_FALSE,
                summary = "Voter registration in Kenya is entirely free of charge. Under Article 38(3) of the Constitution and Section 5(1) of the Elections Act, no fee or administrative charge can be demanded from any Kenyan citizen to register as a voter.",
                groundedContext = "Article 38(3) guarantees free political franchise. Section 5(1) of Elections Act explicitly prohibits fees for continuous voter registration.",
                confidenceScore = 99,
                sourceCategory = "FEES",
                evidenceSources = listOf(
                    EvidenceSource(
                        title = "Constitution of Kenya 2010 — Article 38(3)(a)",
                        legalCitation = "CoK 2010, Art. 38(3)(a)",
                        extractSnippet = "Every adult citizen has the right, without unreasonable restrictions, to be registered as a voter; to vote by secret ballot in any election or referendum; and to be a candidate for public office.",
                        documentType = "CONSTITUTION"
                    ),
                    EvidenceSource(
                        title = "Elections Act No. 24 of 2011 — Section 5(1)",
                        legalCitation = "Cap. 7 Laws of Kenya, Sec. 5(1)",
                        extractSnippet = "The Commission shall continuously register citizens who are eligible as voters at all gazetted registration centres without imposing any fee.",
                        documentType = "STATUTE"
                    )
                )
            ),
            viralCount = 4200
        ),
        TrendingClaim(
            id = "trend_electronic_voting",
            claimTitle = "Paper ballots scrapped for 100% digital electronic voting",
            viralContext = "Viral TikTok video claiming physical ballot papers will not be printed for the next election.",
            verdict = VerdictType.MISLEADING,
            summary = "Kenya uses an electronic system (KIEMS) only for biometric check-in and results transmission. Ballots are physical paper.",
            verification = ClaimVerification(
                id = "claim_electronic_voting_007",
                claimText = "IEBC announced electronic voting replaces ballot papers completely.",
                verdict = VerdictType.MISLEADING,
                summary = "Kenya uses an electronic system (KIEMS) exclusively for voter biometric identification, voter verification, and results transmission. Actual casting of ballots at polling stations is conducted using physical, marked paper ballots deposited in sealed ballot boxes.",
                groundedContext = "Section 44 of the Elections Act mandates an integrated electronic system for identification and transmission, while Section 10 confirms physical marked paper ballots remain the primary method of voting.",
                confidenceScore = 94,
                sourceCategory = "ELECTIONS",
                evidenceSources = listOf(
                    EvidenceSource(
                        title = "Elections Act No. 24 of 2011 — Section 44(1)",
                        legalCitation = "Cap. 7 Laws of Kenya, Sec. 44(1)",
                        extractSnippet = "The Commission shall establish an integrated electronic electoral system that includes biometric voter registration, biometric voter identification, and electronic transmission of election results.",
                        documentType = "STATUTE"
                    ),
                    EvidenceSource(
                        title = "Supreme Court of Kenya — Presidential Petition No. 1 of 2017",
                        legalCitation = "[2017] eKLR, Paragraph 289",
                        extractSnippet = "Kenya's electoral architecture is hybrid: digital at the gate for biometric voter authentication and digital at exit for image transmission of Form 34A, but fundamentally manual and paper-based in mark and deposition.",
                        documentType = "JUDGMENT"
                    )
                )
            ),
            viralCount = 2850
        ),
        TrendingClaim(
            id = "trend_mca_degree",
            claimTitle = "MCA candidates must hold a verified university degree",
            viralContext = "Posts on X claiming aspirants without undergraduate degree certificates cannot appear on ballot.",
            verdict = VerdictType.VERIFIED_FALSE,
            summary = "High Court declared the degree requirement unconstitutional in 2022. Degree is only mandatory for Governor and President.",
            verification = ClaimVerification(
                id = "claim_mca_degree_008",
                claimText = "Any candidate vying for Member of County Assembly (MCA) must have a university degree.",
                verdict = VerdictType.VERIFIED_FALSE,
                summary = "In October 2022, the High Court of Kenya declared Section 22(1)(b) of the Elections Act unconstitutional, ruling that requiring a university degree for Members of County Assembly (MCA) violates Article 38 of the Constitution. A university degree is legally required only for Presidential and Gubernatorial candidates.",
                groundedContext = "High Court Judgment in Petition No. E229 of 2022 (Victor Buoga v. IEBC) voided the degree requirement for County Assembly candidates.",
                confidenceScore = 96,
                sourceCategory = "BILL_OF_RIGHTS",
                evidenceSources = listOf(
                    EvidenceSource(
                        title = "High Court of Kenya — Constitutional Petition No. E229 of 2022",
                        legalCitation = "Buoga v Independent Electoral and Boundaries Commission [2022] eKLR",
                        extractSnippet = "Section 22(1)(b) of the Elections Act requiring a degree qualification for candidates vying for Member of County Assembly is discriminatory, disproportionate, and unconstitutional.",
                        documentType = "JUDGMENT"
                    ),
                    EvidenceSource(
                        title = "Constitution of Kenya 2010 — Article 193(1)",
                        legalCitation = "CoK 2010, Art. 193(1)(a)",
                        extractSnippet = "Unless disqualified under clause (2), a person is eligible for election as a member of a county assembly if the person is a registered voter and satisfies educational requirements prescribed by this Constitution.",
                        documentType = "CONSTITUTION"
                    )
                )
            ),
            viralCount = 1940
        )
    )

    val civicTopics: List<CivicTopic> = listOf(
        CivicTopic(
            id = "topic_voter_rights",
            title = "Your Right to Vote",
            description = "Understand voter eligibility, continuous registration, and protections under Article 38.",
            iconName = "ic_verdict_true",
            guideCount = 1,
            legalBasis = "Constitution Article 38"
        ),
        CivicTopic(
            id = "topic_elections_101",
            title = "Elections 101",
            description = "How Kenyan elections work from polling station ballot to constituency tallying and results.",
            iconName = "ic_library_books",
            guideCount = 1,
            legalBasis = "Elections Act 2011"
        ),
        CivicTopic(
            id = "topic_iebc_mandate",
            title = "Know the IEBC",
            description = "The Commission's powers, duties, commissioner appointments, and legal boundaries.",
            iconName = "ic_scale_justice",
            guideCount = 1,
            legalBasis = "IEBC Act & Article 88"
        ),
        CivicTopic(
            id = "topic_spotting_disinfo",
            title = "Spot Misinformation",
            description = "Practical checks to detect deepfakes, synthetic circulars, and altered Kenyan gazettes.",
            iconName = "ic_info",
            guideCount = 1,
            legalBasis = "Computer Misuse & Cybercrimes Act"
        )
    )

    val civicGuides: List<CivicGuide> = listOf(
        CivicGuide(
            id = "guide_art38",
            topicId = "topic_voter_rights",
            title = "Article 38: Political Rights & Voter Protections",
            subtitle = "Everything Kenyan youth need to know about voting without intimidation, cost, or discrimination.",
            readTimeMinutes = 5,
            primaryCitation = "Constitution of Kenya 2010 — Article 38",
            fullContentHtml = """
                <h3>1. Sovereign Power &amp; Political Franchise</h3>
                <p>The 2010 Constitution of Kenya places all sovereign power squarely in the hands of the people of Kenya under Article 1. Under Article 38, political franchise is an inalienable Bill of Rights guarantee.</p>
                
                <h3>2. Three Pillars of Article 38(3)</h3>
                <p>Every adult Kenyan citizen (aged 18 and above with a National Identity Card or valid Passport) possesses the unconditional right:</p>
                <ul>
                    <li><strong>To be registered as a voter:</strong> Continuous registration across all 290 constituencies without unreasonable fees, discrimination by gender, ethnicity, or economic status.</li>
                    <li><strong>To vote by secret ballot:</strong> Free from coercion, intimidation, or employer penalties under Article 86.</li>
                    <li><strong>To contest for public office:</strong> Subject only to constitutional qualifications.</li>
                </ul>
                
                <h3>3. Continuous Voter Registration (Section 5)</h3>
                <p>Under Section 5 of the Elections Act, IEBC must offer continuous voter registration throughout the year, pausing only close to a general election. The Commission is legally prohibited from charging any fee.</p>
                
                <h3>4. Employer Obligations on Voting Day</h3>
                <p>Employers in both the public and private sectors are legally required to grant reasonable time off to registered employees to travel and cast their ballots without salary deductions.</p>
            """.trimIndent(),
            keyTakeaways = listOf(
                "Voter registration is 100% free of charge nationwide under Section 5(1) of the Elections Act.",
                "Original National ID card or valid Kenyan passport is the only required identity document.",
                "Your vote is completely secret by law under Article 86.",
                "Employers are legally obliged to permit registered employees reasonable time off to cast their ballots."
            )
        ),
        CivicGuide(
            id = "guide_elections_101",
            topicId = "topic_elections_101",
            title = "Elections 101: How Kenyan General Elections Work",
            subtitle = "From the polling station ballot box to national presidential tallying and result finality.",
            readTimeMinutes = 6,
            primaryCitation = "Elections Act No. 24 of 2011 & CoK Articles 101, 136, 180",
            fullContentHtml = """
                <h3>1. The Constitutional Election Date</h3>
                <p>Under Articles 101(1), 136(2)(a), and 180(1) of the Constitution of Kenya 2010, General Elections are held on the <strong>second Tuesday in August every fifth year</strong>. The next Kenyan General Election is scheduled for <strong>Tuesday, August 10, 2027</strong>.</p>
                
                <h3>2. The Six Elective Ballots</h3>
                <p>Every registered voter is entitled to cast six distinct ballots on polling day:</p>
                <ul>
                    <li><strong>President:</strong> Chief of State and Head of Government (National).</li>
                    <li><strong>Member of the National Assembly (MP):</strong> Represents your constituency (290 single-member constituencies).</li>
                    <li><strong>County Woman Representative:</strong> Represents women interests in the National Assembly (47 counties, reserved exclusively for female candidates under Article 97(1)(b)).</li>
                    <li><strong>Senator:</strong> Protects devolved county governance in the Senate (47 counties).</li>
                    <li><strong>County Governor:</strong> Chief Executive of the County Government (47 counties).</li>
                    <li><strong>Member of County Assembly (MCA):</strong> Represents your ward in the County Assembly (1,450 wards).</li>
                </ul>
                
                <h3>3. The Landmark Maina Kiai Rule (Form 34A Finality)</h3>
                <p>Under the landmark Court of Appeal and Supreme Court ruling in <em>IEBC v. Maina Kiai (2017)</em>, presidential results declared by the Presiding Officer at the polling station and recorded on <strong>Form 34A</strong> are final. Neither the Constituency Returning Officer nor the National Returning Officer (IEBC Chairperson) has legal power to alter or correct these figures.</p>
                
                <h3>4. Presidential Victory Threshold (50% + 1)</h3>
                <p>To win the presidency in the first round under Article 138(4), a candidate must receive:</p>
                <ul>
                    <li>More than half of all votes cast in the election (50% plus 1 vote); AND</li>
                    <li>At least 25% of the votes cast in each of more than half of the 47 counties (at least 24 counties).</li>
                </ul>
            """.trimIndent(),
            keyTakeaways = listOf(
                "Kenyan General Elections take place every 5 years on the second Tuesday of August.",
                "Voters cast 6 separate ballots for national and devolved governance.",
                "Polling station results declared on Form 34A are final and cannot be altered by anyone (Maina Kiai precedent).",
                "Presidential victory requires 50% + 1 of national votes and 25% in at least 24 counties."
            )
        ),
        CivicGuide(
            id = "guide_iebc_mandate",
            topicId = "topic_iebc_mandate",
            title = "Understanding the IEBC: Powers, Mandate & Boundaries",
            subtitle = "The constitutional role, commissioner appointments, and legal boundaries of the electoral body.",
            readTimeMinutes = 5,
            primaryCitation = "Constitution of Kenya 2010 — Article 88 & IEBC Act",
            fullContentHtml = """
                <h3>1. Constitutional Foundation of the IEBC</h3>
                <p>The Independent Electoral and Boundaries Commission (IEBC) is established under Article 88 of the Constitution as a Chapter 15 independent commission. In the exercise of its functions, the Commission is subject only to the Constitution and the law, free from executive or political direction.</p>
                
                <h3>2. Core Mandates under Article 88(4)</h3>
                <ul>
                    <li><strong>Conducting &amp; Supervising Elections:</strong> Managing all referenda and elections to any elective body established by the Constitution.</li>
                    <li><strong>Continuous Voter Registration:</strong> Maintaining and updating the National Register of Voters.</li>
                    <li><strong>Boundary Delimitation:</strong> Reviewing names and boundaries of constituencies and wards at intervals of between 8 and 12 years.</li>
                    <li><strong>Electoral Dispute Resolution:</strong> Settlement of electoral disputes, including nomination disputes (excluding election petitions).</li>
                    <li><strong>Voter Education &amp; Code of Conduct:</strong> Facilitating civic voter education and enforcing the Electoral Code of Conduct.</li>
                </ul>
                
                <h3>3. Reconstitution &amp; Commissioner Appointments</h3>
                <p>Under the IEBC (Amendment) Act 2024, commissioners are not unilaterally chosen by the President. A statutory 9-member Selection Panel (drawn from the Parliamentary Service Commission, Law Society of Kenya, and Interfaith Councils) competitively interviews applicants and transmits nominated names to Parliament for public vetting before formal presidential appointment.</p>
                
                <h3>4. Limits of Commission Authority</h3>
                <p>The IEBC cannot arbitrarily cancel elections, alter polling station figures, or disqualify nominated candidates without due process and judicial review by the High Court.</p>
            """.trimIndent(),
            keyTakeaways = listOf(
                "IEBC is an independent Chapter 15 commission accountable solely to the Constitution.",
                "Recruitment of commissioners requires an independent multi-sector Selection Panel and parliamentary vetting.",
                "The Commission resolves pre-election nomination disputes, while election petitions belong to the Judiciary.",
                "IEBC has a constitutional duty under Article 88 to conduct continuous voter registration nationwide."
            )
        ),
        CivicGuide(
            id = "guide_spotting_disinfo",
            topicId = "topic_spotting_disinfo",
            title = "Spotting Electoral Misinformation & Deepfakes in Kenya",
            subtitle = "Practical checks to detect fake gazettes, manipulated audio, and misleading political circulars.",
            readTimeMinutes = 6,
            primaryCitation = "Computer Misuse and Cybercrimes Act, 2018 (Sec. 22 & 23)",
            fullContentHtml = """
                <h3>1. Penal Consequences of Disinformation</h3>
                <p>Under Sections 22 and 23 of the <strong>Computer Misuse and Cybercrimes Act (No. 5 of 2018)</strong>, publishing false, fabricated, or misleading information calculated to cause panic, chaos, or electoral disruption carries penalties of up to <strong>Ksh 5,000,000 in fines or 10 years imprisonment</strong>.</p>
                
                <h3>2. Common Forms of Kenyan Electoral Disinformation</h3>
                <ul>
                    <li><strong>Fabricated Gazette Notices:</strong> Doctored PDF files bearing forged Republic of Kenya crests alleging postponed voting dates or candidate disqualifications.</li>
                    <li><strong>AI Voice Cloning:</strong> Synthesized audio recordings mimicking prominent politicians discussing ballot rigging or ethnic mobilization.</li>
                    <li><strong>Counterfeit Press Statements:</strong> Fake circulars using outdated IEBC logos or forged signatures of Commission officials.</li>
                    <li><strong>Fee Rumors:</strong> False claims that voters must pay money to register or replace lost voting cards.</li>
                </ul>
                
                <h3>3. How to Spot AI Deepfakes &amp; Audio Clones</h3>
                <p>AI audio often lacks genuine ambient room echo, features unnatural micro-pauses between sentences, and struggles with authentic Sheng or Swahili colloquial inflection. Always check if the alleged audio was covered by verified mainstream media broadcasters.</p>
                
                <h3>4. The HakiCheck 4-Step Verification Checklist</h3>
                <ol>
                    <li><strong>Verify the Gazette:</strong> Check Kenya Law Reports official archive at <em>kenyalaw.org</em> for the real Gazette Notice number.</li>
                    <li><strong>Check Official Portals:</strong> Confirm statements directly from the verified IEBC website (<em>iebc.or.ke</em>) or verified social media channels (@IEBCKenya).</li>
                    <li><strong>Consult Primary Statutes:</strong> Confirm whether the claim contradicts the Constitution of Kenya 2010.</li>
                    <li><strong>Use HakiCheck AI:</strong> Submit the claim to HakiCheck to receive real-time, grounded legal citations from indexed primary statutes.</li>
                </ol>
            """.trimIndent(),
            keyTakeaways = listOf(
                "Spreading deliberately false electoral information is a serious crime under the Computer Misuse Act.",
                "Genuine government gazette notices always have traceable volume numbers on kenyalaw.org.",
                "AI audio clones can be identified through robotic cadence, absent background noise, and odd pronunciation.",
                "Always cross-reference political rumors with primary statutory sources before sharing on social media."
            )
        )
    )

    fun detectDialect(query: String, preferredLanguage: String = "English"): String {
        return "english"
    }

    fun hasNegation(query: String): Boolean {
        val lower = query.lowercase()
        val negWords = setOf(
            "not", "cannot", "can't", "cant", "never", "no", "unable", "prohibited", "barred", "illegal",
            "forbidden", "disqualified", "denied", "without", "refused",
            "si", "sio", "hawezi", "hawawezi", "haruhusiwi", "hawaruhusiwi", "marufuku", "haiwezekani",
            "hakuna", "hapana", "haitambuliwi", "haitwi", "hata", "wala", "bila",
            "haezi", "hawaezi", "haingii", "noball", "haina"
        )
        val tokens = lower.split(Regex("[^a-zA-Z']+")).toSet()
        if (tokens.any { it in negWords }) return true
        val prefixes = listOf("hawezi", "hawawezi", "hawaruhusiwi", "haruhusiwi", "haiwezekani", "hawaezi", "haezi", "cannot", "can't", "bila malipo")
        return prefixes.any { lower.contains(it) }
    }

    fun isQuestion(query: String): Boolean {
        val lower = query.trim().lowercase()
        val starters = listOf(
            "who", "what", "when", "where", "why", "how", "can i", "can a", "is it", "does", "which",
            "nani", "nini", "lini", "wapi", "vipi", "kwa nini", "mbona", "je", "inawezekana", "naweza", "naeza", "siku", "tarehe", "kwani", "ati"
        )
        val questionWords = listOf("ngapi", "a-je", "au la", "ama vipi", "ni lini", "ni nani", "ni nini", "fomu ni gani", "lini", "nani", "nini", "wapi", "vipi", "gani", "aje", "kwani", "ati", "siku", "tarehe")
        return lower.endsWith("?") || starters.any { lower.startsWith(it) } || questionWords.any { lower.contains(it) }
    }

    fun findOrGenerateVerification(query: String, preferredLanguage: String = "English"): ClaimVerification {
        val cleanQuery = query.trim().lowercase()
        val isQueryQuestion = isQuestion(query)

        // 1. Check exact or high-overlap match against trending claims
        for (trend in trendingClaims) {
            val trendKeywords = trend.claimTitle.lowercase().split(" ")
                .filter { it.length > 3 && it !in listOf("this", "that", "with", "from", "kenyan", "kenya") }
            val matchCount = trendKeywords.count { cleanQuery.contains(it) }
            if (matchCount >= 2 || cleanQuery.contains(trend.claimTitle.lowercase().take(20))) {
                return trend.verification.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query
                )
            }
        }

        // 2. Check exact or high-overlap match against verifiedCorpus
        for (item in verifiedCorpus) {
            val itemKeywords = item.claimText.lowercase().split(" ")
                .filter { it.length > 3 && it !in listOf("this", "that", "with", "from", "kenyan", "kenya") }
            val matchCount = itemKeywords.count { cleanQuery.contains(it) }
            if (matchCount >= 2 || cleanQuery.contains(item.claimText.lowercase().take(20))) {
                return item.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query
                )
            }
        }

        fun getById(id: String): ClaimVerification =
            verifiedCorpus.firstOrNull { it.id == id } ?: verifiedCorpus[0]

        // Context-aware fallback heuristics for common civic patterns (All in clear, non-redundant English)
        return when {
            // Linda Mama / SHA / SHIF
            cleanQuery.contains("linda mama") || (cleanQuery.contains("maternity") && (cleanQuery.contains("sha") || cleanQuery.contains("shif"))) -> {
                val trend = trendingClaims.firstOrNull { it.id == "trend_linda_mama" }
                trend?.verification?.copy(id = UUID.randomUUID().toString(), claimText = query)
                    ?: ClaimVerification(
                        id = UUID.randomUUID().toString(),
                        claimText = query,
                        verdict = VerdictType.MISLEADING,
                        summary = "Under Article 43(1)(a) of the Constitution and the Social Health Insurance Act 2023, the Linda Mama free maternity program has not been scrapped. Maternal services have been integrated into the Social Health Authority (SHA) Primary Healthcare Fund, retaining free maternal delivery care in public health facilities.",
                        groundedContext = "CoK 2010 Article 43(1)(a) and Social Health Insurance Act 2023.",
                        confidenceScore = 95,
                        sourceCategory = "HEALTH",
                        evidenceSources = listOf(
                            EvidenceSource(
                                title = "Constitution of Kenya 2010 — Article 43(1)(a)",
                                legalCitation = "CoK 2010, Art. 43(1)(a)",
                                extractSnippet = "Every person has the right to the highest attainable standard of health, which includes the right to health care services, including reproductive health care.",
                                documentType = "CONSTITUTION"
                            )
                        )
                    )
            }

            // Edwin Sifuna / Protests
            cleanQuery.contains("sifuna") || (cleanQuery.contains("protest") && cleanQuery.contains("bill")) -> {
                val trend = trendingClaims.firstOrNull { it.id == "trend_sifuna_protest" }
                trend?.verification?.copy(id = UUID.randomUUID().toString(), claimText = query)
                    ?: ClaimVerification(
                        id = UUID.randomUUID().toString(),
                        claimText = query,
                        verdict = VerdictType.VERIFIED_FALSE,
                        summary = "Under Article 37 of the Constitution of Kenya 2010, every citizen has the protected right to assemble, demonstrate, and picket peaceably and unarmed. Senator Edwin Sifuna has not sponsored any bill banning or criminalizing peaceful protests; the Assembly and Demonstration Bill was sponsored by MP Peter Kaluma.",
                        groundedContext = "Article 37 of the Constitution and official parliamentary records.",
                        confidenceScore = 98,
                        sourceCategory = "BILL_OF_RIGHTS",
                        evidenceSources = listOf(
                            EvidenceSource(
                                title = "Constitution of Kenya 2010 — Article 37",
                                legalCitation = "CoK 2010, Art. 37",
                                extractSnippet = "Every person has the right, peaceably and unarmed, to assemble, to demonstrate, to picket, and to present petitions to public authorities.",
                                documentType = "CONSTITUTION"
                            )
                        )
                    )
            }

            // Housing Levy
            cleanQuery.contains("housing levy") || cleanQuery.contains("affordable housing") -> {
                val trend = trendingClaims.firstOrNull { it.id == "trend_housing_levy" }
                trend?.verification?.copy(id = UUID.randomUUID().toString(), claimText = query)
                    ?: ClaimVerification(
                        id = UUID.randomUUID().toString(),
                        claimText = query,
                        verdict = VerdictType.VERIFIED_FALSE,
                        summary = "Under Section 4 of the Affordable Housing Act 2024, the 1.5% Affordable Housing Levy applies to both the gross salary of formal employees and the gross income of non-salaried or informal sector workers, without a blanket informal exemption.",
                        groundedContext = "Affordable Housing Act 2024 Section 4.",
                        confidenceScore = 97,
                        sourceCategory = "TAXATION",
                        evidenceSources = listOf(
                            EvidenceSource(
                                title = "Affordable Housing Act 2024 — Section 4",
                                legalCitation = "Act No. 2 of 2024, Sec. 4",
                                extractSnippet = "The levy shall be at the rate of one point five per centum of the gross salary of an employee or the gross income of a person other than an employee.",
                                documentType = "STATUTE"
                            )
                        )
                    )
            }

            // IEBC Selection Panel
            cleanQuery.contains("selection panel") || (cleanQuery.contains("iebc") && cleanQuery.contains("appoint")) -> {
                val trend = trendingClaims.firstOrNull { it.id == "trend_iebc_selection" }
                trend?.verification?.copy(id = UUID.randomUUID().toString(), claimText = query)
                    ?: ClaimVerification(
                        id = UUID.randomUUID().toString(),
                        claimText = query,
                        verdict = VerdictType.VERIFIED_FALSE,
                        summary = "Under Article 88 of the Constitution and the IEBC (Amendment) Act 2024, the President cannot directly appoint commissioners. Nominees must be selected by a 9-member multi-stakeholder Selection Panel representing Parliament, the Law Society of Kenya, and religious organizations.",
                        groundedContext = "Article 88 and IEBC (Amendment) Act 2024.",
                        confidenceScore = 99,
                        sourceCategory = "ELECTIONS",
                        evidenceSources = listOf(
                            EvidenceSource(
                                title = "IEBC (Amendment) Act 2024 — First Schedule",
                                legalCitation = "Act No. 4 of 2024",
                                extractSnippet = "The Selection Panel shall invite applications, consider the qualifications, shortlist and interview applicants, and forward the names to the President.",
                                documentType = "STATUTE"
                            )
                        )
                    )
            }

            // Presidential Term Limits
            cleanQuery.contains("term limit") || cleanQuery.contains("7 year") || cleanQuery.contains("seven year") || cleanQuery.contains("third term") || cleanQuery.contains("3rd term") -> {
                val trend = trendingClaims.firstOrNull { it.id == "trend_term_limit" }
                trend?.verification?.copy(id = UUID.randomUUID().toString(), claimText = query)
                    ?: ClaimVerification(
                        id = UUID.randomUUID().toString(),
                        claimText = query,
                        verdict = VerdictType.VERIFIED_FALSE,
                        summary = "Under Article 142(2) of the Constitution of Kenya 2010, the term of office of the President is strictly two five-year terms. Under Article 255(1)(f), any constitutional amendment affecting the term of office of the President requires an affirmative national referendum.",
                        groundedContext = "CoK 2010 Article 142(2) and Article 255(1)(f).",
                        confidenceScore = 99,
                        sourceCategory = "ELECTIONS",
                        evidenceSources = listOf(
                            EvidenceSource(
                                title = "Constitution of Kenya 2010 — Article 142(2)",
                                legalCitation = "CoK 2010, Art. 142(2)",
                                extractSnippet = "A person shall not hold office as President for more than two terms.",
                                documentType = "CONSTITUTION"
                            )
                        )
                    )
            }

            // MCA Degree Requirement (Photo 2 query)
            cleanQuery.contains("degree") || cleanQuery.contains("university") || cleanQuery.contains("mca") -> {
                val base = getById("claim_mca_degree_003")
                val isAffirming = cleanQuery.contains("without") || cleanQuery.contains("not need") || cleanQuery.contains("no degree") || cleanQuery.contains("hakuna") || cleanQuery.contains("haitaji") || cleanQuery.contains("free")
                val isNegativeClaim = cleanQuery.contains("must") || cleanQuery.contains("needs") || cleanQuery.contains("requires") || cleanQuery.contains("lazima") || cleanQuery.contains("mandatory")
                val (verdict, summaryText) = if (isAffirming) {
                    VerdictType.VERIFIED_TRUE to "In October 2022, the High Court of Kenya (Petition No. E229 of 2022) declared Section 22(1)(b) of the Elections Act unconstitutional, nullifying the university degree requirement for Member of County Assembly (MCA) candidates under Article 38(3)(c)."
                } else if (isNegativeClaim) {
                    VerdictType.VERIFIED_FALSE to "In October 2022, the High Court of Kenya (Petition No. E229 of 2022) declared Section 22(1)(b) of the Elections Act unconstitutional. Candidates for Member of County Assembly (MCA) are not legally required to hold a university degree."
                } else {
                    VerdictType.CIVIC_INFO to "Under the High Court declaration in Petition No. E229 of 2022, university degree requirements for Member of County Assembly (MCA) aspirants were struck down as unconstitutional under Article 38(3)(c)."
                }
                base.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = verdict,
                    summary = summaryText
                )
            }

            // Voter Registration Fee / 500 Ksh (Photo 1 query)
            cleanQuery.contains("fee") || cleanQuery.contains("cost") || cleanQuery.contains("pay") || cleanQuery.contains("500") || cleanQuery.contains("charge") || cleanQuery.contains("bure") || cleanQuery.contains("sare") || cleanQuery.contains("malipo") || cleanQuery.contains("chapaa") || cleanQuery.contains("ganji") || cleanQuery.contains("pesa") || cleanQuery.contains("ndururu") -> {
                val isFreeClaim = cleanQuery.contains("bure") || cleanQuery.contains("sare") || cleanQuery.contains("free") || cleanQuery.contains("bila malipo") || cleanQuery.contains("no fee") || cleanQuery.contains("zero fee")
                val isOpenCostQ = isQueryQuestion && (cleanQuery.contains("how much") || cleanQuery.contains("ngapi") || cleanQuery.contains("cost")) && !cleanQuery.contains("500") && !cleanQuery.contains("pay") && !cleanQuery.contains("malipo")
                val base = getById("claim_voter_fee_001")
                val (verdict, summaryText) = if (isFreeClaim) {
                    VerdictType.VERIFIED_TRUE to "Voter registration in Kenya is entirely free of charge under Article 38(3) of the Constitution of Kenya 2010 and Section 5(1) of the Elections Act 2011. The IEBC charges no fees to register citizens."
                } else if (isOpenCostQ) {
                    VerdictType.CIVIC_INFO to "Voter registration in Kenya is entirely free under Article 38(3) of the Constitution and Section 5(1) of the Elections Act. The IEBC cannot charge any fees."
                } else {
                    VerdictType.VERIFIED_FALSE to "Under Article 38(3) of the Constitution of Kenya 2010 and Section 5(1) of the Elections Act 2011, voter registration is entirely free of charge. The IEBC cannot impose any fee, charge, or monetary condition on citizens registering to vote."
                }
                base.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = verdict,
                    summary = summaryText
                )
            }

            // Woman President
            (cleanQuery.contains("woman") || cleanQuery.contains("women") || cleanQuery.contains("female") || cleanQuery.contains("mwanamke") || cleanQuery.contains("wanawake") || cleanQuery.contains("mama") || cleanQuery.contains("dada")) && (cleanQuery.contains("president") || cleanQuery.contains("presidency") || cleanQuery.contains("rais") || cleanQuery.contains("prezo") || cleanQuery.contains("state house")) && !cleanQuery.contains("woman rep") && !cleanQuery.contains("women rep") && !cleanQuery.contains("woman representative") -> {
                val hasNeg = hasNegation(query)
                val (verdict, summaryText) = if (hasNeg) {
                    VerdictType.VERIFIED_FALSE to "Under Article 27(3) (Gender Equality) and Article 137(1) of the Constitution of Kenya 2010, women have the full constitutional right to run for President. Any qualified adult citizen by birth can contest regardless of gender."
                } else if (isQueryQuestion) {
                    VerdictType.CIVIC_INFO to "Under Article 27(3) (Equality of Men and Women) and Article 137(1) of the Constitution of Kenya 2010, any female Kenyan citizen by birth who is a registered voter and qualifies for election as an MP is legally eligible to run for President."
                } else {
                    VerdictType.VERIFIED_TRUE to "Under Article 27(3) (Gender Equality), Article 38(3)(c) (Right to stand for public office), and Article 137(1) of the Constitution of Kenya 2010, women possess the full constitutional right to vie for the presidency. There is no gender bar to the presidency in Kenya."
                }
                ClaimVerification(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = verdict,
                    summary = summaryText,
                    groundedContext = "Constitution of Kenya 2010 Articles 27(3), 38(3)(c), and 137(1) (Equal Political Franchise).",
                    confidenceScore = 99,
                    sourceCategory = "ELECTIONS",
                    evidenceSources = listOf(
                        EvidenceSource(
                            title = "Constitution of Kenya 2010 — Article 27(3)",
                            legalCitation = "CoK 2010, Art. 27(3)",
                            extractSnippet = "Women and men have the right to equal treatment, including the right to equal opportunities in political, economic, cultural and social spheres.",
                            documentType = "CONSTITUTION"
                        ),
                        EvidenceSource(
                            title = "Constitution of Kenya 2010 — Article 137(1)",
                            legalCitation = "CoK 2010, Art. 137(1)",
                            extractSnippet = "A person is qualified for nomination as a presidential candidate if the person is a citizen by birth, is qualified to stand for election as a member of Parliament, and is nominated by a registered political party or is an independent candidate.",
                            documentType = "CONSTITUTION"
                        )
                    )
                )
            }

            // Raila President
            cleanQuery.contains("raila") && (cleanQuery.contains("president") || cleanQuery.contains("prezo") || cleanQuery.contains("rais") || cleanQuery.contains("won")) -> {
                val base = getById("claim_raila_president_000")
                base.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = VerdictType.VERIFIED_FALSE,
                    summary = "William Samoei Ruto was officially sworn in as the 5th President of Kenya on September 13, 2022 under Article 141 of the Constitution following the August 2022 General Election. Raila Odinga is an opposition leader and not the Head of State."
                )
            }

            // Election Date
            (cleanQuery.contains("election") || cleanQuery.contains("voting") || cleanQuery.contains("uchaguzi") || cleanQuery.contains("kura") || cleanQuery.contains("elex") || cleanQuery.contains("vote")) && (cleanQuery.contains("december") || cleanQuery.contains("desemba") || cleanQuery.contains("november") || cleanQuery.contains("january") || cleanQuery.contains("2026") || cleanQuery.contains("2025") || cleanQuery.contains("2028")) -> {
                val base = getById("claim_election_date_000")
                base.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = VerdictType.VERIFIED_FALSE,
                    summary = "Under Articles 101(1), 136(2)(a), and 180(1) of the Constitution of Kenya 2010, General Elections are held on the second Tuesday of August every fifth year. The next Kenyan General Election is on Tuesday, August 10, 2027."
                )
            }
            (cleanQuery.contains("election") || cleanQuery.contains("voting") || cleanQuery.contains("uchaguzi") || cleanQuery.contains("kura") || cleanQuery.contains("elex") || cleanQuery.contains("vote")) && (cleanQuery.contains("date") || cleanQuery.contains("when") || cleanQuery.contains("day") || cleanQuery.contains("take place") || cleanQuery.contains("tarehe") || cleanQuery.contains("lini") || cleanQuery.contains("siku")) -> {
                val base = getById("claim_election_date_000")
                base.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = VerdictType.CIVIC_INFO,
                    summary = "Under Articles 101(1), 136(2)(a), and 180(1) of the Constitution of Kenya 2010, General Elections are held on the second Tuesday of August every fifth year. The next Kenyan General Election is scheduled for Tuesday, August 10, 2027."
                )
            }

            // Presidential Age
            (cleanQuery.contains("president") || cleanQuery.contains("rais") || cleanQuery.contains("urais") || cleanQuery.contains("prezo")) && (cleanQuery.contains("35") || cleanQuery.contains("70") || cleanQuery.contains("upper age") || cleanQuery.contains("maximum age") || cleanQuery.contains("kikomo cha juu")) -> {
                val base = getById("claim_presidential_age_000")
                base.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = VerdictType.VERIFIED_FALSE,
                    summary = "Under the Constitution of Kenya 2010 (Article 137(1) and Article 99(1)(a)), any registered adult voter aged 18 or older is qualified to vie for President. There is no 35-year minimum or 70-year maximum age limit."
                )
            }
            (cleanQuery.contains("how old") || cleanQuery.contains("age") || cleanQuery.contains("miaka") || cleanQuery.contains("umri")) && (cleanQuery.contains("president") || cleanQuery.contains("rais") || cleanQuery.contains("urais") || cleanQuery.contains("prezo")) -> {
                val base = getById("claim_presidential_age_000")
                base.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = VerdictType.CIVIC_INFO,
                    summary = "Under Article 137(1) of the Constitution of Kenya 2010, a presidential candidate must be a citizen by birth and qualified to stand for election as an MP. Under Article 99(1)(a) and Article 83(1)(a), any registered adult voter aged 18 or older is legally eligible, with no separate upper age limit."
                )
            }

            // Nairobi Governor
            (cleanQuery.contains("governor") || cleanQuery.contains("gavana")) && (cleanQuery.contains("nairobi") || cleanQuery.contains("kanairo") || cleanQuery.contains("who is") || cleanQuery.contains("wa sasa")) -> {
                val base = getById("claim_nairobi_governor_000")
                base.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = VerdictType.CIVIC_INFO,
                    summary = "The current Governor of Nairobi County is Johnson Arthur Sakaja, who was elected in the August 2022 General Election and sworn into office pursuant to Article 180 of the Constitution of Kenya 2010 and the County Governments Act."
                )
            }

            // Woman Representative
            cleanQuery.contains("woman rep") || cleanQuery.contains("women rep") || cleanQuery.contains("woman representative") || cleanQuery.contains("mwakilishi wa wanawake") -> {
                val hasManRunning = cleanQuery.contains("man") || cleanQuery.contains("men") || cleanQuery.contains("mwanaume") || cleanQuery.contains("chali") || cleanQuery.contains("jamaa")
                val base = getById("claim_woman_rep_000")
                val (verdict, summaryText) = if (hasManRunning) {
                    VerdictType.VERIFIED_FALSE to "Under Article 97(1)(b) of the Constitution of Kenya 2010, the County Woman Representative position is constitutionally reserved for women elected by the registered voters of each county. Men are not qualified to stand for this seat."
                } else {
                    VerdictType.CIVIC_INFO to "Under Article 97(1)(b) and Article 99 of the Constitution of Kenya 2010, the County Woman Representative seat is contested exclusively by female Kenyan citizens who are registered adult voters."
                }
                base.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = verdict,
                    summary = summaryText
                )
            }

            // Voting Age / 18
            cleanQuery.contains("under 18") || cleanQuery.contains("under eighteen") || cleanQuery.contains("chini ya 18") -> {
                val base = getById("claim_voting_age_000")
                base.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = VerdictType.VERIFIED_FALSE,
                    summary = "Under Article 38(3) and Article 83(1)(a) of the Constitution of Kenya 2010, only an adult citizen has the right to be registered and vote. Article 260 defines an adult as an individual who has attained 18 years. Minors under 18 cannot legally vote."
                )
            }
            (cleanQuery.contains("18") || cleanQuery.contains("eighteen") || cleanQuery.contains("adult") || cleanQuery.contains("miaka 18") || cleanQuery.contains("kumi na minane")) && (cleanQuery.contains("vote") || cleanQuery.contains("voter") || cleanQuery.contains("register") || cleanQuery.contains("kura") || cleanQuery.contains("kujiandikisha")) -> {
                val hasNeg = hasNegation(query)
                getById("claim_voting_age_000").copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = if (hasNeg) VerdictType.VERIFIED_FALSE else if (isQueryQuestion) VerdictType.CIVIC_INFO else VerdictType.VERIFIED_TRUE,
                    summary = "Under Article 83(1)(a) and Article 38(3) of the Constitution of Kenya 2010, every adult citizen who has attained 18 years of age has the constitutional right to register and vote."
                )
            }

            // Polling Station Results / Maina Kiai
            cleanQuery.contains("final") || cleanQuery.contains("result") || cleanQuery.contains("34a") || cleanQuery.contains("maina kiai") || cleanQuery.contains("fomu 34a") || cleanQuery.contains("mwisho") -> {
                val hasNeg = hasNegation(query) || cleanQuery.contains("altered") || cleanQuery.contains("can be changed") || cleanQuery.contains("not final") || cleanQuery.contains("si ya mwisho") || cleanQuery.contains("inaweza kubadilishwa")
                val base = getById("claim_maina_kiai_005")
                val (verdict, summaryText) = if (isQueryQuestion) {
                    VerdictType.CIVIC_INFO to "Under the Court of Appeal Maina Kiai judgment upheld by the Supreme Court, presidential results declared at the polling station and recorded on Form 34A are final and can only be altered by the Supreme Court upon an election petition."
                } else if (hasNeg) {
                    VerdictType.VERIFIED_FALSE to "Under the landmark Maina Kiai judgment upheld by the Supreme Court, presidential results declared at the polling station and recorded on Form 34A are final and cannot be altered by any returning officer or the IEBC Chairperson."
                } else {
                    VerdictType.VERIFIED_TRUE to "Under the Court of Appeal Maina Kiai judgment upheld by the Supreme Court, presidential results declared at the polling station and recorded on Form 34A are final and can only be altered by the Supreme Court upon an election petition."
                }
                base.copy(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = verdict,
                    summary = summaryText
                )
            }

            // Inmates / Prisoners Voting
            cleanQuery.contains("wafungwa") || cleanQuery.contains("jela") || cleanQuery.contains("prisoner") || cleanQuery.contains("prison") -> {
                val hasNeg = hasNegation(query)
                val (verdict, summaryText) = if (isQueryQuestion) {
                    VerdictType.CIVIC_INFO to "The Supreme Court of Kenya confirmed that convicted prisoners in lawful custody have the constitutional right to register and vote under Article 38 of the Constitution of Kenya 2010, specifically participating in the presidential election."
                } else if (hasNeg) {
                    VerdictType.VERIFIED_FALSE to "Under Article 38 of the Constitution of Kenya 2010 and Supreme Court precedent, convicted prisoners have the protected right to register and vote in presidential elections."
                } else {
                    VerdictType.VERIFIED_TRUE to "The Supreme Court of Kenya confirmed that convicted prisoners in lawful custody have the constitutional right to register and vote under Article 38 of the Constitution of Kenya 2010, specifically participating in the presidential election."
                }
                ClaimVerification(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = verdict,
                    summary = summaryText,
                    groundedContext = "CoK 2010 Article 38 and Supreme Court Petition (Kituo Cha Sheria v IEBC).",
                    confidenceScore = 98,
                    sourceCategory = "BILL_OF_RIGHTS",
                    evidenceSources = listOf(
                        EvidenceSource(
                            title = "Constitution of Kenya 2010 — Article 38(3)",
                            legalCitation = "CoK 2010, Art. 38(3)",
                            extractSnippet = "Every adult citizen has the right, without unreasonable restrictions, to be registered as a voter and to vote in elections.",
                            documentType = "CONSTITUTION"
                        )
                    )
                )
            }

            // Compulsory Voting
            cleanQuery.contains("compulsory") || cleanQuery.contains("mandatory") || cleanQuery.contains("lazima") -> {
                val isCompulsoryClaim = !hasNegation(query) && !isQueryQuestion
                ClaimVerification(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = if (isCompulsoryClaim) VerdictType.VERIFIED_FALSE else VerdictType.CIVIC_INFO,
                    summary = "Voting in Kenya is voluntary under Article 38 of the Constitution of Kenya 2010. While civic participation is encouraged, no citizen is legally compelled to cast a ballot or subject to penalties for abstaining.",
                    groundedContext = "CoK 2010 Article 38 (Political franchise is a voluntary democratic right, not a penal mandate).",
                    confidenceScore = 99,
                    sourceCategory = "ELECTIONS",
                    evidenceSources = listOf(
                        EvidenceSource(
                            title = "Constitution of Kenya 2010 — Article 38",
                            legalCitation = "CoK 2010, Art. 38",
                            extractSnippet = "Every citizen is free to make political choices, which includes the right to form, or participate in forming, a political party; to participate in the activities of a party; and to vote or not vote.",
                            documentType = "CONSTITUTION"
                        )
                    )
                )
            }

            // Diaspora Voting
            cleanQuery.contains("diaspora") || cleanQuery.contains("abroad") || cleanQuery.contains("ughaibuni") || cleanQuery.contains("majuu") || cleanQuery.contains("ngambo") -> {
                val hasNeg = hasNegation(query)
                val (verdict, summaryText) = if (isQueryQuestion) {
                    VerdictType.CIVIC_INFO to "Kenyan citizens living in the diaspora have the constitutional right to register and vote under Article 38 of the Constitution and Section 5 of the Elections Act. Diaspora voting is conducted at designated Kenyan embassies and high commissions for presidential elections."
                } else if (hasNeg) {
                    VerdictType.VERIFIED_FALSE to "Under Article 38 of the Constitution of Kenya 2010 and Section 5 of the Elections Act, Kenyan citizens living in the diaspora have the protected constitutional right to register and vote in presidential elections at designated overseas embassies."
                } else {
                    VerdictType.VERIFIED_TRUE to "Kenyan citizens living in the diaspora have the constitutional right to register and vote under Article 38 of the Constitution and Section 5 of the Elections Act."
                }
                ClaimVerification(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = verdict,
                    summary = summaryText,
                    groundedContext = "Elections Act Section 5 and Constitution Article 38 (Political Franchise for Diaspora).",
                    confidenceScore = 98,
                    sourceCategory = "ELECTIONS",
                    evidenceSources = listOf(
                        EvidenceSource(
                            title = "Elections Act No. 24 of 2011 — Section 5",
                            legalCitation = "Cap. 7 Laws of Kenya, Sec. 5",
                            extractSnippet = "The Commission shall register citizens residing outside Kenya who are entitled to be registered as voters for presidential elections.",
                            documentType = "STATUTE"
                        )
                    )
                )
            }

            // Fallback for uncategorized
            else -> {
                ClaimVerification(
                    id = UUID.randomUUID().toString(),
                    claimText = query,
                    verdict = VerdictType.UNVERIFIED,
                    summary = "There is insufficient specific data in the verified Kenyan legal corpus to confirm or refute this claim conclusively. Citizens are advised to verify against official IEBC Gazettes or primary statutory texts.",
                    groundedContext = "Query fell outside the pre-indexed statutory corpus.",
                    confidenceScore = 40,
                    sourceCategory = "UNCATEGORIZED",
                    evidenceSources = emptyList()
                )
            }
        }
    }
}
