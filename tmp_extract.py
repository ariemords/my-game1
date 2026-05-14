from pathlib import Path
s = Path('index.html').read_text('utf-8')
out = []
i = 0
while True:
    idx = s.find('<script', i)
    if idx < 0:
        break
    start = s.find('>', idx)
    if start < 0:
        break
    end = s.find('</script>', start)
    if end < 0:
        break
    out.append(s[start+1:end])
    i = end + 9
Path('tmp_script_check.js').write_text('\n'.join(out), encoding='utf-8')
print('wrote', len(out), 'script blocks, length', len('\n'.join(out)))
