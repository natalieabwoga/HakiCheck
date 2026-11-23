import { useState, type ReactNode } from 'react'

type IconName =
  | 'book'
  | 'battery'
  | 'check'
  | 'copy'
  | 'document'
  | 'flame'
  | 'gear'
  | 'library'
  | 'mic'
  | 'share'
  | 'signal'
  | 'verify'
  | 'wifi'

function Icon({ name, size = 20 }: { name: IconName; size?: number }) {
  const paths: Record<IconName, ReactNode> = {
    book: <><path d="M4 5.5A2.5 2.5 0 0 1 6.5 3H11v16H6.5A2.5 2.5 0 0 0 4 21.5v-16Z"/><path d="M20 5.5A2.5 2.5 0 0 0 17.5 3H13v16h4.5a2.5 2.5 0 0 1 2.5 2.5v-16Z"/></>,
    battery: <><rect x="3" y="7" width="17" height="10" rx="2"/><path d="M22 10v4"/><path d="M6 10h10v4H6z"/></>,
    check: <path d="m5 12 4 4L19 6"/>,
    copy: <><rect x="9" y="9" width="11" height="11" rx="2"/><path d="M15 9V6a2 2 0 0 0-2-2H6a2 2 0 0 0-2 2v7a2 2 0 0 0 2 2h3"/></>,
    document: <><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8Z"/><path d="M14 2v6h6M8 13h8M8 17h6"/></>,
    flame: <path d="M12 22c4.2 0 7-3.1 7-7.2 0-3.3-1.8-6.1-5.2-8.8.1 2.4-.7 4-2.1 5.1.1-3.8-2-6.5-4.1-8.1.2 3-2.6 5.4-2.6 9.8C5 18 8 22 12 22Z"/>,
    gear: <><circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.7 1.7 0 0 0 .3 1.9l.1.1-2.8 2.8-.1-.1a1.7 1.7 0 0 0-1.9-.3 1.7 1.7 0 0 0-1 1.6v.2h-4V21a1.7 1.7 0 0 0-1-1.6 1.7 1.7 0 0 0-1.9.3l-.1.1L4.2 17l.1-.1a1.7 1.7 0 0 0 .3-1.9A1.7 1.7 0 0 0 3 14H2.8v-4H3a1.7 1.7 0 0 0 1.6-1 1.7 1.7 0 0 0-.3-1.9L4.2 7 7 4.2l.1.1A1.7 1.7 0 0 0 9 4.6a1.7 1.7 0 0 0 1-1.6v-.2h4V3a1.7 1.7 0 0 0 1 1.6 1.7 1.7 0 0 0 1.9-.3l.1-.1L19.8 7l-.1.1a1.7 1.7 0 0 0-.3 1.9 1.7 1.7 0 0 0 1.6 1h.2v4H21a1.7 1.7 0 0 0-1.6 1Z"/></>,
    library: <><path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"/><path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2Z"/></>,
    mic: <><rect x="9" y="2" width="6" height="12" rx="3"/><path d="M5 10a7 7 0 0 0 14 0M12 17v4M9 21h6"/></>,
    share: <><circle cx="18" cy="5" r="3"/><circle cx="6" cy="12" r="3"/><circle cx="18" cy="19" r="3"/><path d="m8.6 10.5 6.8-4M8.6 13.5l6.8 4"/></>,
    signal: <path d="M4 18v-3M9 18v-6M14 18V9M19 18V5"/>,
    verify: <><path d="M12 3 20 7v5c0 5-3.4 8.3-8 9.5C7.4 20.3 4 17 4 12V7l8-4Z"/><path d="m8.5 12 2.2 2.2 4.8-5"/></>,
    wifi: <><path d="M5 9.5a11 11 0 0 1 14 0M8 13a6.5 6.5 0 0 1 8 0"/><path d="M12 17h.01"/></>,
  }

  return (
    <svg width={size} height={size} viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
      {paths[name]}
    </svg>
  )
}

function ActionButton({
  children,
  variant = 'secondary',
  onClick,
  ariaLabel,
}: {
  children: ReactNode
  variant?: 'primary' | 'secondary' | 'ghost'
  onClick?: () => void
  ariaLabel?: string
}) {
  return <button className={`action-button ${variant}`} onClick={onClick} aria-label={ariaLabel}>{children}</button>
}

function App() {
  const sampleClaim = 'IEBC has increased the voter registration fee to KES 1,000 for 2027.'
  const [claim, setClaim] = useState('')
  const [verifiedClaim, setVerifiedClaim] = useState(sampleClaim)
  const [notice, setNotice] = useState('')
  const [activeTab, setActiveTab] = useState('Verify')
  const [darkMode, setDarkMode] = useState(false)
  const [dataCollection, setDataCollection] = useState(false)
  const [profile, setProfile] = useState({ name: 'Amani Kamau', county: 'Nairobi', language: 'English' })

  const verify = () => {
    setVerifiedClaim(claim.trim() || sampleClaim)
    setNotice('Verification updated from grounded civic sources.')
  }

  const copyLink = async () => {
    await navigator.clipboard?.writeText(window.location.href)
    setNotice('Verification link copied.')
  }

  const navItems: { label: string; icon: IconName }[] = [
    { label: 'Verify', icon: 'verify' },
    { label: 'Civic Knowledge', icon: 'library' },
    { label: 'Trending Claims', icon: 'flame' },
    { label: 'Settings', icon: 'gear' },
  ]

  return (
    <main className="app-shell">
      <section className={darkMode ? 'phone-frame dark' : 'phone-frame'} aria-label="HakiCheck mobile application">
        <div className="status-bar" aria-label="Device status">
          <span>9:41</span>
          <div className="status-icons">
            <Icon name="signal" size={16} />
            <Icon name="wifi" size={16} />
            <Icon name="battery" size={19} />
          </div>
        </div>

        <header className="app-header">
          <div>
            <div className="brand-kicker"><span className="kenya-mark" /> AI CIVIC ASSISTANT</div>
            <h1>HakiCheck</h1>
          </div>
          <div className="grounded-pill">
            <Icon name="check" size={13} />
            <span>Grounded by<br />Constitution &amp; IEBC Laws</span>
          </div>
        </header>

        {activeTab === 'Verify' && <div className="scroll-area">
          <section className="claim-card" aria-labelledby="claim-heading">
            <div className="section-label" id="claim-heading">VERIFY A CLAIM</div>
            <textarea
              value={claim}
              onChange={(event) => setClaim(event.target.value)}
              placeholder="Paste a political claim, quote, or social media text (English, Swahili, Sheng)..."
              aria-label="Political claim to verify"
            />
            <div className="claim-actions">
              <span className="text-only-note">Text claims only</span>
              <ActionButton variant="primary" onClick={verify}>
                Verify Claim <span className="button-arrow">→</span>
              </ActionButton>
            </div>
          </section>

          {notice && <div className="toast" role="status">{notice}</div>}

          <article className="result-card">
            <div className="result-topline">
              <span className="false-badge"><span className="badge-dot" /> VERIFIED: FALSE</span>
              <span className="checked-time">Checked now</span>
            </div>
            <blockquote>“{verifiedClaim}”</blockquote>
            <div className="divider" />
            <div className="summary-heading"><span className="summary-icon"><Icon name="verify" size={16} /></span> HakiCheck summary</div>
            <p className="summary">This claim is false. Kenyan citizens are not charged to register as voters; both the Constitution and IEBC’s operational guidance protect free access to voter registration.</p>
          </article>

          <section className="sources-section" aria-labelledby="sources-heading">
            <div className="section-heading-row">
              <div>
                <span className="eyebrow">EVIDENCE TRAIL</span>
                <h2 id="sources-heading">Grounded Sources</h2>
              </div>
              <span className="extract-count">2 legal extracts</span>
            </div>
            <div className="source-list">
              <article className="source-card">
                <div className="source-icon red"><Icon name="book" size={19} /></div>
                <div><h3>Constitution of Kenya 2010 — Article 38(3)</h3><p>Every adult citizen has the right to register as a voter without fee constraints.</p></div>
                <span className="source-arrow">↗</span>
              </article>
              <article className="source-card">
                <div className="source-icon green"><Icon name="document" size={19} /></div>
                <div><h3>Elections Act (Cap. 7) &amp; IEBC Operational Plan</h3><p>Voter registration conducted by the Commission is free of charge.</p></div>
                <span className="source-arrow">↗</span>
              </article>
            </div>
          </section>

          <section className="community-actions" aria-label="Share and feedback">
            <div className="share-row">
              <ActionButton onClick={copyLink}><Icon name="copy" size={16} /> Copy link</ActionButton>
              <ActionButton onClick={() => setNotice('Share sheet opened for WhatsApp and TikTok.')}><Icon name="share" size={16} /> Share</ActionButton>
            </div>
            <div className="feedback-row">
              <span>Was this context clear?</span>
              <div>
                <ActionButton variant="ghost" ariaLabel="Yes, this was clear" onClick={() => setNotice('Thanks for your feedback.')}>Yes</ActionButton>
                <ActionButton variant="ghost" ariaLabel="No, this was not clear" onClick={() => setNotice('Thanks. We’ll improve this explanation.')}>Not quite</ActionButton>
              </div>
            </div>
          </section>
        </div>}

        {activeTab === 'Civic Knowledge' && (
          <div className="scroll-area page-view">
            <div className="page-intro">
              <span className="eyebrow">LEARN YOUR RIGHTS</span>
              <h2>Civic Knowledge</h2>
              <p>Clear, non-partisan guides to voting and government in Kenya.</p>
            </div>
            <div className="featured-guide">
              <span className="guide-number">01</span>
              <div className="source-icon green"><Icon name="verify" size={20} /></div>
              <span className="guide-tag">5 MIN GUIDE</span>
              <h3>Your right to vote</h3>
              <p>Understand voter eligibility, registration, and protections under Article 38.</p>
              <ActionButton variant="primary" onClick={() => setNotice('Opening voter rights guide.')}>Start learning →</ActionButton>
            </div>
            <div className="section-heading-row page-section-heading">
              <div><span className="eyebrow">ESSENTIALS</span><h2>Explore topics</h2></div>
            </div>
            <div className="knowledge-grid">
              {[
                ['Elections 101', 'How elections work, from registration to results.', 'book'],
                ['Your Constitution', 'Everyday rights explained in plain language.', 'document'],
                ['Know the IEBC', 'The Commission’s role, powers, and duties.', 'library'],
                ['Spot Misinformation', 'Simple checks before you share a claim.', 'verify'],
              ].map(([title, description, icon]) => (
                <button className="knowledge-card" key={title} onClick={() => setNotice(`Opening ${title}.`)}>
                  <span className="source-icon green"><Icon name={icon as IconName} size={18} /></span>
                  <strong>{title}</strong>
                  <span>{description}</span>
                </button>
              ))}
            </div>
          </div>
        )}

        {activeTab === 'Trending Claims' && (
          <div className="scroll-area page-view">
            <div className="page-intro">
              <span className="eyebrow">PUBLIC CONVERSATION</span>
              <h2>Trending Claims</h2>
              <p>Frequently shared claims, checked against trusted civic records.</p>
            </div>
            <div className="trend-filter" role="group" aria-label="Trending claim filters">
              <button className="selected">All</button><button>Elections</button><button>Economy</button><button>Rights</button>
            </div>
            <div className="trend-list">
              {[
                ['FALSE', 'Voter registration now costs KES 1,000.', '12.8k checks', 'red'],
                ['TRUE', 'A Kenyan citizen can register at age 18.', '8.4k checks', 'green'],
                ['MISLEADING', 'You need a passport to register as a voter.', '5.7k checks', 'amber'],
                ['TRUE', 'Every registered voter has the right to a secret ballot.', '3.2k checks', 'green'],
              ].map(([status, text, checks, tone], index) => (
                <button className="trend-card" key={text} onClick={() => { setClaim(text); setActiveTab('Verify') }}>
                  <span className="trend-rank">{String(index + 1).padStart(2, '0')}</span>
                  <span className="trend-content">
                    <span className={`status-badge ${tone}`}>{status}</span>
                    <strong>{text}</strong>
                    <small><Icon name="flame" size={12} /> {checks} this week</small>
                  </span>
                  <span className="source-arrow">→</span>
                </button>
              ))}
            </div>
          </div>
        )}

        {activeTab === 'Settings' && (
          <div className="scroll-area page-view">
            <div className="page-intro">
              <span className="eyebrow">YOUR HAKICHECK</span>
              <h2>Settings</h2>
              <p>Manage your profile, appearance, and privacy choices.</p>
            </div>

            <section className="settings-card" aria-labelledby="profile-heading">
              <div className="settings-title">
                <span className="source-icon green"><Icon name="gear" size={18} /></span>
                <div><h3 id="profile-heading">Profile details</h3><p>Used to personalize your civic experience.</p></div>
              </div>
              <label>Full name<input value={profile.name} onChange={(e) => setProfile({ ...profile, name: e.target.value })} /></label>
              <div className="field-row">
                <label>County<select value={profile.county} onChange={(e) => setProfile({ ...profile, county: e.target.value })}><option>Nairobi</option><option>Mombasa</option><option>Kisumu</option><option>Nakuru</option><option>Kiambu</option></select></label>
                <label>Language<select value={profile.language} onChange={(e) => setProfile({ ...profile, language: e.target.value })}><option>English</option><option>Kiswahili</option><option>Sheng</option></select></label>
              </div>
              <ActionButton onClick={() => setNotice('Profile details saved.')}>Save profile</ActionButton>
            </section>

            <section className="settings-card">
              <div className="settings-title">
                <span className="source-icon green"><Icon name="verify" size={18} /></span>
                <div><h3>Preferences</h3><p>Choose how HakiCheck looks and handles your data.</p></div>
              </div>
              <div className="setting-row">
                <div><strong>Dark mode</strong><span>Use a darker, low-light appearance.</span></div>
                <button className={darkMode ? 'switch on' : 'switch'} role="switch" aria-checked={darkMode} onClick={() => setDarkMode(!darkMode)}><span /></button>
              </div>
              <div className="setting-row privacy-row">
                <div><strong>Help improve HakiCheck</strong><span>Allow anonymized queries to be used for research and product improvement. Your choice is optional.</span></div>
                <button className={dataCollection ? 'switch on' : 'switch'} role="switch" aria-checked={dataCollection} onClick={() => setDataCollection(!dataCollection)}><span /></button>
              </div>
              <div className="privacy-note"><Icon name="verify" size={15} /><span>Your queries are <strong>{dataCollection ? 'included' : 'not included'}</strong> in data collection. You can change this anytime.</span></div>
            </section>

            {notice && <div className="toast" role="status">{notice}</div>}
          </div>
        )}

        <nav className="bottom-nav" aria-label="Main navigation">
          {navItems.map((item) => (
            <button key={item.label} className={activeTab === item.label ? 'nav-item active' : 'nav-item'} onClick={() => setActiveTab(item.label)}>
              <span className="nav-icon"><Icon name={item.icon} size={20} /></span>
              <span>{item.label}</span>
            </button>
          ))}
        </nav>
      </section>
    </main>
  )
}

export default App
