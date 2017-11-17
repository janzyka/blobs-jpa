# Test scenario (Mac OSX)
1. Run local postgres - testing with H2 in-memory DB doesn't make sense as the blob is stored in-memory 
2. Start app with decent memory limit, like `-Xmx50M` 
3. Open `jvisualvm`
4. Test and watch memory consumption in `jvisualvm`
```bash
mkfile -n 500m /tmp/data.dat
curl -D - -F "file=@/tmp/data.dat" http://localhost:8080/blobs
curl -o /tmp/data.dat.download __Location header from previous command__
```